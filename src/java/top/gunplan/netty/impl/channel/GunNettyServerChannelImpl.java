/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;


import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionType;
import top.gunplan.netty.SystemChannelChangedHandle;
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
public class GunNettyServerChannelImpl extends BaseGunNettyChannel<ServerSocketChannel, GunConnEventLoop, GunNettyParentPipeline>
        implements GunNettyServerChannel<ServerSocketChannel> {
    private final SystemChannelChangedHandle handle;

    GunNettyServerChannelImpl(final ServerSocketChannel channel, final SystemChannelChangedHandle pipeline, final GunConnEventLoop eventLoop, final long id) throws IOException {
        super(null, channel, id);
        this.registerEventLoop(eventLoop);
        this.handle = pipeline;
    }

    @Override
    public GunNettyParentPipeline pipeline() {
        throw new GunException(GunExceptionType.NOT_SUPPORT, "not support pipeline");
    }


    @Override
    public SocketAddress remoteAddress() {
        throw new GunException(GunExceptionType.NOT_SUPPORT, "not support");
    }


    @Override
    public boolean isValid() {
        return isOpen();
    }


    @Override
    @SafeVarargs
    public final GunNettyServerChannel<ServerSocketChannel> bind(int... port) throws IOException {
        for (int p : port) {
            channel().bind(new InetSocketAddress(p));
        }
        handle.whenBind(port);
        return this;
    }

    @Override
    public void registerAcceptWithEventLoop() throws IOException {
        channel().configureBlocking(false);
        channel().register(eventLoop.select(), SelectionKey.OP_ACCEPT, null);
    }

    @Override
    public void close() throws IOException {
        super.close();
        eventLoop.select().close();
    }

    @Override
    public void doTime() {

    }
}
