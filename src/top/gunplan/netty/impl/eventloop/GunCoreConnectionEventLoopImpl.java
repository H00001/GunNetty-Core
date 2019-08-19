/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.SystemChannelChangedHandle;
import top.gunplan.netty.common.GunNettyContext;
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

/**
 * GunCoreConnectionEventLoopImpl deal connection event
 *
 * @author dosdrtt
 * @see AbstractGunCoreEventLoop
 */
class GunCoreConnectionEventLoopImpl extends AbstractGunCoreEventLoop implements GunConnEventLoop {
    private volatile int[] port;
    private volatile SystemChannelChangedHandle initHandle;
    private volatile ChannelInitHandle childrenInitHandle;
    private volatile GunNettyServerChannel<ServerSocketChannel> channel;

    GunCoreConnectionEventLoopImpl() throws IOException {

    }

    @Override
    public int openPort(int... port) {
        this.port = port;
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
    public int init(ExecutorService service, SystemChannelChangedHandle handle, ChannelInitHandle childrenHandle) throws IOException {
        super.init(service);
        assert handle != null;
        assert childrenHandle != null;
        this.initHandle = handle;
        this.childrenInitHandle = childrenHandle;
        channel = GunNettyChannelFactory.newServerChannel(ServerSocketChannel.open(), initHandle, this);
        channel.bind(port[0]).registerAcceptWithEventLoop(this);
        return 0;
    }


    @Override
    public boolean isLoopNext() {
        try {
            return bootSelector.select() > 0 && isRunning();
        } catch (IOException e) {
            GunNettyContext.logger.error(e);
            return false;
        }
    }

    @Override
    public void nextDeal() {
        Iterator keyIterator = bootSelector.selectedKeys().iterator();
        while (keyIterator.hasNext()) {
            SelectionKey sk = (SelectionKey) keyIterator.next();
            try {
                this.dealEvent(sk);
            } catch (IOException e) {
                channel.pipeline().parentHandel().dealExceptionEvent(new GunChannelException(e));
            }
            keyIterator.remove();
        }
    }

    @Override
    void whenHaltDeal() throws IOException {
        channel.close();
    }


    @Override
    public void dealEvent(SelectionKey key) throws IOException {
        final SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
        this.deal.submit(() -> {
            GunNettyChildChannel<SocketChannel> childChannel =
                    GunNettyChannelFactory.newChannel(socketChannel,
                            childrenInitHandle, channel, null);
            manager.transferEventLoop().push(new GunNettyChannelTransferImpl<>(childChannel));
            BaseGunNettyWorker worker = new GunAcceptWorker(childChannel);
            if (worker.init() == 0) {
                worker.run();
            }
        });
    }

}