/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.impl.channel.BaseGunNettyChannel;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;
import top.gunplan.netty.impl.channel.GunNettyServerChannel;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyPipeline;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelImpl
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-08 23:09
 */
class GunNettyChannelImpl extends BaseGunNettyChannel<SocketChannel, GunDataEventLoop<SocketChannel>> implements GunNettyChildChannel<SocketChannel> {
    private GunNettyServerChannel pChannel;

    GunNettyChannelImpl(final SocketChannel channel, final GunNettyPipeline pipeline, GunNettyServerChannel pChannel, final long seq, GunDataEventLoop<SocketChannel> eventLoop) {
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
    public void closeAndRemove() {

    }


    @Override
    public GunNettyServerChannel parent() {
        return pChannel;
    }

    @Override
    public GunNettyChildChannel<SocketChannel> setSelectionKey(SelectionKey key) {
        return null;
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
