/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunNettyReadObserve;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * GunNettyChannelImpl
 *
 * @author frank albert
 * @version 0.0.0.4
 * @date 2019-08-08 23:09
 */
class GunNettyChannelImpl extends BaseGunNettyChannel<SocketChannel, GunDataEventLoop<SocketChannel>, GunNettyChildrenPipeline>
        implements GunNettyChildChannel<SocketChannel> {
    private GunNettyServerChannel pChannel;
    private volatile SelectionKey key;
    private List<GunNettyReadObserve> observes = new CopyOnWriteArrayList<>();

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
    public SocketAddress remoteAddress() {
        try {
            return channel().getRemoteAddress();
        } catch (IOException e) {
            throw new GunException(e);
        }
    }

    @Override
    public SocketAddress localAddress() throws IOException {
        return channel().getLocalAddress();
    }

    //if we do not set key
    @Override
    public boolean isValid() {
        return key == null || key.isValid();
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
    public void closeAndRemove(boolean notHappenedOnRead) {
        try {
            close();
            eventLoop.fastLimit();
            if (notHappenedOnRead) {
                eventLoop.decreaseAndStop();
            }
            observes.parallelStream().forEach(v -> v.onClose(remoteAddress()));
        } catch (IOException e) {
            observes.parallelStream().forEach(v -> v.whenCloseMeetException(remoteAddress(), e));
        }
    }


    @Override
    public GunNettyServerChannel parent() {
        return pChannel;
    }

    @Override
    public void registerReadWithEventLoop(GunDataEventLoop<SocketChannel> eventLoop) {
        try {
            eventLoop.registerReadKey(channel());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public GunNettyChildChannel<SocketChannel> addReadObserve(GunNettyReadObserve observe) {
        observes.add(observe);
        return this;
    }

    @Override
    public GunNettyChildChannel<SocketChannel> cleanAllObserve() {
        observes.clear();
        return this;
    }

    private void continueLoop() {
        eventLoop.incrAndContinueLoop();
    }

    @Override
    public void recoverReadInterest() {
        key.interestOps(SelectionKey.OP_READ);
        continueLoop();
        observes.parallelStream().forEach(GunNettyReadObserve::onRecoverReadInterest);
    }

    @Override
    public void setKey(SelectionKey key) {
        this.key = key;
    }
}
