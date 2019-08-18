/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelImpl
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-08 23:09
 */
class GunNettyChannelImpl extends BaseGunNettyChannel<SocketChannel, GunDataEventLoop<SocketChannel>, GunNettyChildrenPipeline>
        implements GunNettyChildChannel<SocketChannel> {
    private GunNettyServerChannel pChannel;

    GunNettyChannelImpl(final SocketChannel channel,
                        final GunNettyChildrenPipeline pipeline,
                        final GunNettyServerChannel<ServerSocketChannel> pChannel,
                        final GunDataEventLoop<SocketChannel> eventLoop,
                        final long seq
    ) {
        super(pipeline, channel, seq, eventLoop);
        this.pChannel = pChannel;
    }


    @Override
    public SocketAddress remoteAddress() throws IOException {
        return channel().getRemoteAddress();

    }

    @Override
    public SocketAddress localAddress() throws IOException {
        return channel().getLocalAddress();
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public boolean isOpen() {
        return channel().isOpen();
    }

    @Override
    public void close() throws IOException {
        channel().close();
    }

    @Override
    public void closeAndRemove() throws IOException {
        close();
        eventLoop.fastLimit();
        eventLoop.decreaseAndStop();
    }


    @Override
    public GunNettyServerChannel parent() {
        return pChannel;
    }

    @Override
    public void registerReadWithEventLoop(GunDataEventLoop<SocketChannel> eventLoop) {

    }

    @Override
    public GunNettyChildChannel<SocketChannel> addReadObserve() {
        return null;
    }

    @Override
    public GunNettyChildChannel<SocketChannel> cleanAllObserve() {
        return null;
    }

    @Override
    public void continueLoop() {
        eventLoop.incrAndContinueLoop();
    }
}
