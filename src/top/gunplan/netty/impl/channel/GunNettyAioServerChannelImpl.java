package top.gunplan.netty.impl.channel;

import top.gunplan.netty.impl.eventloop.GunConnEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyParentPipeline;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;

public class GunNettyAioServerChannelImpl extends BaseGunNettyChannel<ServerSocketChannel, GunConnEventLoop, GunNettyParentPipeline>
        implements GunNettyServerChannel<ServerSocketChannel> {


    GunNettyAioServerChannelImpl(GunNettyParentPipeline pipeline, ServerSocketChannel channel, long id) {
        super(pipeline, channel, id);

    }

    @Override
    public void doTime() {

    }

    @Override
    public GunNettyServerChannel<ServerSocketChannel> bind(int... port) throws IOException {
        return null;
    }

    @Override
    public void registerAcceptWithEventLoop() throws IOException {

    }

    @Override
    public SocketAddress remoteAddress() {
        return null;
    }

    @Override
    public SocketAddress localAddress() {
        return null;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public boolean isOpen() {
        return false;
    }
}

