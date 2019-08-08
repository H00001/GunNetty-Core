/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelImpl
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-08 23:09
 */
class GunNettyChannelImpl implements GunNettyChannel<SocketChannel> {
    private final SocketChannel channel;
    private final long id;
    private final GunNettyPipeline pipeline;


    GunNettyChannelImpl(final SocketChannel channel, final GunNettyPipeline pipeline, final long seq) {
        this.channel = channel;
        this.id = seq;
        this.pipeline = pipeline;
    }

    @Override
    public GunNettyPipeline pipeline() {
        return pipeline;
    }

    @Override
    public SocketChannel channel() {
        return channel;
    }

    @Override
    public GunNettyChannel setPipeline(GunNettyPipeline pipeline) {
        return null;
    }

    @Override
    public long channelId() {
        return 0;
    }

    @Override
    public GunNettyChannel setChannelId(long id) {
        return null;
    }


    @Override
    public SocketAddress remoteAddress() throws IOException {
        return channel.getRemoteAddress();

    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void close() throws IOException {

    }
}
