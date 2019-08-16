/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;


import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionType;
import top.gunplan.netty.GunNettyParentHandle;
import top.gunplan.netty.impl.eventloop.GunConnEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyParentPipeline;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;


/**
 * @author dosdrtt
 */
public class GunNettyServerChannelImpl extends BaseGunNettyChannel<ServerSocketChannel, GunConnEventLoop, GunNettyParentHandle>
        implements GunNettyServerChannel<ServerSocketChannel> {

    GunNettyServerChannelImpl(final ServerSocketChannel channel, final GunNettyParentPipeline pipeline, final GunConnEventLoop eventLoop, final long id) {
        super(pipeline, channel, id, eventLoop);
    }

    @Override
    public GunNettyParentPipeline pipeline() {
        return (GunNettyParentPipeline) super.pipeline();
    }


    @Override
    public SocketAddress remoteAddress() {
        throw new GunException(GunExceptionType.NOT_SUPPORT, "not support");
    }

    @Override
    public SocketAddress localAddress() throws IOException {
        return channel().getLocalAddress();
    }

    @Override
    public boolean isValid() {
        return true;
    }


    @Override
    public boolean isOpen() {
        return channel().isOpen();
    }


    @Override
    public GunNettyServerChannel<ServerSocketChannel> bind(int... port) throws IOException {
        channel().bind(new InetSocketAddress(port[0]));
        return this;
    }

    @Override
    public void registerAcceptWithEventLoop(GunConnEventLoop eventLoop) throws IOException {
        channel().configureBlocking(false);
        channel().register(eventLoop.select(), SelectionKey.OP_ACCEPT, null);
    }
}
