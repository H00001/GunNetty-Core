/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.impl.GunNettyChannel;
import top.gunplan.netty.impl.GunNettyChannelFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
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
    private final ServerSocketChannel var57;
    private volatile int[] port;
    private volatile ChannelInitHandle initHandle;

    GunCoreConnectionEventLoopImpl() throws IOException {
        var57 = ServerSocketChannel.open();
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


    public int init(ExecutorService deal, ChannelInitHandle initHandle) throws IOException {
        super.init(deal);
        this.initHandle = initHandle;
        var57.bind(new InetSocketAddress(port[0])).configureBlocking(false);
        var57.register(bootSelector, SelectionKey.OP_ACCEPT, this);
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
                e.getStackTrace();
                //todo
                // pipeline.handel().dealExceptionEvent(new GunChannelException(e));
            }
            keyIterator.remove();
        }
    }

    @Override
    void whenHaltDeal() throws IOException {
        var57.close();
    }

    @Override
    public void initInitHandle(ChannelInitHandle handle) {
        this.initHandle = handle;
    }

    @Override
    public void dealEvent(SelectionKey key) throws IOException {
        final SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
        this.deal.submit(() -> {
            GunNettyChannel<SocketChannel> channel = GunNettyChannelFactory.newChannel(socketChannel, initHandle);
            manager.transferThread().push(new GunNettyChannelTransferImpl(channel));
            BaseGunNettyWorker worker = new GunAcceptWorker(channel);
            if (worker.init() == 0) {
                worker.run();
            }
        });
    }
}