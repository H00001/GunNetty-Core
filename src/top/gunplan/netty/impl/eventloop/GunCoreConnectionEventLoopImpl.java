/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.common.GunNettyContext;

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


    @Override
    public int init(ExecutorService deal, GunNettyPipeline pipeline) throws IOException {
        super.init(deal, pipeline);
        var57.bind(new InetSocketAddress(port[0])).configureBlocking(false);
        var57.register(bootSelector, SelectionKey.OP_ACCEPT, this);
        return 0;
    }

    @Override
    public boolean isLoopNext() {
        try {
            return bootSelector.select() > 0 && isRunning();
        } catch (IOException e) {
            GunNettyContext.logger.error(e.getMessage());
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
                pipeline.handel().dealExceptionEvent(new GunChannelException(e));
            }
            keyIterator.remove();
        }
    }

    @Override
    void whenHaltDeal() throws IOException {
        var57.close();
    }


    @Override
    public void dealEvent(SelectionKey key) throws IOException {
        final SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
        manager.transferThread().push(new GunNettyChannelTransferImpl(0, socketChannel));
        BaseGunNettyWorker worker = new GunAcceptWorker(pipeline, socketChannel);
        if (worker.init() == 0) {
            this.deal.submit(worker);
        }
    }
}
