/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.eventloop.GunNettyTransferEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;
import top.gunplan.netty.observe.GunNettyChannelObserve;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * GunNettyChildrenChannelImpl
 *
 * @author frank albert
 * @version 0.0.0.5
 * @date 2019-08-08 23:09
 */
class GunNettyChildrenChannelImpl extends BaseGunNettyChannel<SocketChannel, GunDataEventLoop<SocketChannel>, GunNettyChildrenPipeline>
        implements GunNettyChildChannel<SocketChannel> {
    private GunNettyServerChannel pChannel;
    private volatile SelectionKey key;
    private final SocketAddress remoteAddress;
    private final SocketAddress localAddress;
    private List<GunNettyChannelObserve> observes = new CopyOnWriteArrayList<>();

    GunNettyChildrenChannelImpl(final SocketChannel channel,
                                final GunNettyChildrenPipeline pipeline,
                                final GunNettyServerChannel<ServerSocketChannel> pChannel,
                                final long seq
    ) throws IOException {
        super(pipeline, channel, seq);
        this.pChannel = pChannel;
        this.remoteAddress = channel.getRemoteAddress();
        this.localAddress = channel.getLocalAddress();
    }


    @Override
    public SocketAddress remoteAddress() {
        return remoteAddress;
    }

    @Override
    public SocketAddress localAddress() {
        return localAddress;
    }


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
    public GunNettyChannel<SocketChannel, GunDataEventLoop<SocketChannel>, GunNettyChildrenPipeline> closeAndRemove(boolean notHappenedOnRead) {
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
        return this;
    }


    @Override
    public GunNettyServerChannel parent() {
        return pChannel;
    }

    /**
     * only called by {@link GunNettyTransferEventLoop}
     */
    @Override
    public void registerReadWithEventLoop() {
        try {
            channel().configureBlocking(false);
            channel().socket().setTcpNoDelay(true);
            this.key = eventLoop.registerReadKey(channel());
            this.key.attach(this);
            observes.parallelStream().forEach(v -> v.whenRegister(channel()));
        } catch (IOException e) {
            observes.parallelStream().forEach(v -> v.whenRegisterMeetException(channel(), e));
        }
    }


    @Override
    public GunNettyChildChannel<SocketChannel> addReadObserve(GunNettyChannelObserve observe) {
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
        observes.parallelStream().forEach(GunNettyChannelObserve::onRecoverReadInterest);
    }


    @Override
    public void time() {
        timers.parallelStream().forEach(t -> {
            if (unsafeSeqencer.nextSequence() % t.timeInterval() == 0) {
                t.doWork(this);
            }
        });
    }
}
