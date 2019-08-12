/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionType;
import top.gunplan.netty.impl.eventloop.GunConnEventLoop;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;


/**
 * @author dosdrtt
 */
public class GunNettyServerChannelImpl extends BaseGunNettyChannel<ServerSocketChannel, GunConnEventLoop> implements GunNettyServerChannel {

    GunNettyServerChannelImpl(final ServerSocketChannel channel, final GunNettyPipeline pipeline, final long id, final GunConnEventLoop eventLoop) {
        super(pipeline, channel, id, eventLoop);
    }

    @Override
    public GunNettyPipeline pipeline() {
        return null;
    }

    @Override
    public SocketAddress remoteAddress() throws IOException {
        throw new GunException(GunExceptionType.NOT_SUPPORT, "not support");
    }

    @Override
    public SocketAddress localAddress() throws IOException {
        return channel().getLocalAddress();
    }


    @Override
    public boolean isOpen() {
        return channel().isOpen();
    }


    @Override
    public ServerSocketChannel bind(int... port) throws IOException {
        return channel().bind(new InetSocketAddress(port[0]));
    }
}
