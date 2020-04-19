/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.SystemChannelChangedHandle;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.impl.channel.GunNettyChannelException;
import top.gunplan.netty.impl.channel.GunNettyChannelFactory;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;
import top.gunplan.netty.impl.channel.GunNettyServerChannel;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * GunCoreConnectionEventLoopImpl deal connection event
 *
 * @author dosdrtt
 * @see AbstractGunCoreEventLoop
 */
class GunCoreConnectionEventLoopImpl extends AbstractGunCoreEventLoop implements GunConnEventLoop {
    private volatile int[] port;
    private final SystemChannelChangedHandle initHandle;
    private volatile ChannelInitHandle childrenInitHandle;
    private volatile GunNettyServerChannel<ServerSocketChannel> channel;

    GunCoreConnectionEventLoopImpl(SystemChannelChangedHandle handle, ChannelInitHandle childrenHandle) {
        this.initHandle = handle;
        this.childrenInitHandle = childrenHandle;
        assert handle != null;
        assert childrenHandle != null;
    }

    @Override
    public int openPort(int... port) throws IOException {
        this.port = port;
        channel.bind(port[0]).registerAcceptWithEventLoop();
        return 0;
    }

    @Override
    public int listenPort() {
        return this.port[0];
    }

    @Override
    public Selector select() {
        return bootSelector;
    }

    @Override
    public int init(ExecutorService service) {
        try {
            super.init(service);
            channel = GunNettyChannelFactory.newServerChannel(ServerSocketChannel.open(), initHandle, this);
        } catch (IOException e) {
            this.initHandle.failToCreateParentChannel(e);
        }
        assert channel != null;
        return 0;
    }


    @Override
    public boolean isLoopNext() {
        try {
            return bootSelector.select() > 0 && super.isLoopNext();
        } catch (IOException e) {
            GunNettyContext.logger.error(e);
            return false;
        }

    }

    @Override
    public void nextDeal() {
        Iterator<SelectionKey> keyIterator = bootSelector.selectedKeys().iterator();
        while (keyIterator.hasNext()) {
            SelectionKey sk = keyIterator.next();
            try {
                this.dealEvent(sk);
            } catch (IOException e) {
                channel.pipeline().parentHandel().dealExceptionEvent(new GunNettyChannelException(null, e));
            }
            keyIterator.remove();
        }
    }

    @Override
    void whenHaltDeal() throws IOException {
        channel.close();
        initHandle.connEventLoopStop(this);
    }


    @Override
    public void dealEvent(SelectionKey key) throws IOException {
        final SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();

        this.submit(() -> {
            GunNettyChildChannel<SocketChannel> childChannel;
            try {
                childChannel = GunNettyChannelFactory.newChannel(socketChannel, childrenInitHandle, channel);
            } catch (IOException e) {
                initHandle.failToCreateChildChannel(e);
                return;
            }
            manager.transferEventLoop().push(new GunNettyChannelTransferImpl<>(childChannel));
            BaseGunNettyWorker worker = new GunAcceptWorker(childChannel);
            if (worker.init() == 0) {
                worker.run();
            }
        });
    }

    @Override
    public void stopEventLoop() {
        super.stopEventLoop();
        try {
            Thread.sleep(100);
            bootSelector.close();
        } catch (InterruptedException | IOException ignore) {

        }
    }

    @Override
    public void whenSendTaskFail(Runnable r, RejectedExecutionException e) {
        GunNettyExecutors.executeByNewThread(r);
        throw new GunEventLoopException(GunEventLoopExceptionType.EXECUTE_TASK_FAIL,e);
    }
}