/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.filter.GunNettyFilter;
import top.gunplan.netty.impl.GunNettyStdFirstFilter;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.eventloop.GunNettyTransferEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;
import top.gunplan.netty.impl.timeevent.GunNettyTimeExecutor;
import top.gunplan.netty.observe.GunNettyChildChannelObserve;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * GunNettyChildrenChannelImpl
 *
 * @author frank albert
 * @version 0.0.0.6
 */
class GunNettyChildrenChannelImpl extends BaseGunNettyChannel<SocketChannel,
        GunDataEventLoop<SocketChannel>, GunNettyChildrenPipeline>
        implements GunNettyChildChannel<SocketChannel> {
    private GunNettyServerChannel<ServerSocketChannel> pChannel;
    private volatile SelectionKey key;
    private final SocketAddress remoteAddress;
    private Consumer<GunNettyChildChannel<SocketChannel>> readCompleteCallBack;
    private ConcurrentLinkedQueue<ByteBuffer> writeBuffer = new ConcurrentLinkedQueue<>();

    GunNettyChildrenChannelImpl(final SocketChannel channel,
                                final GunNettyChildrenPipeline pipeline,
                                final GunNettyServerChannel<ServerSocketChannel> pChannel,
                                final long seq
    ) throws IOException {
        super(pipeline, channel, seq);
        this.pChannel = pChannel;
        this.remoteAddress = channel.getRemoteAddress();
    }


    @Override
    public SocketAddress remoteAddress() {
        return remoteAddress;
    }


    @Override
    public boolean isValid() {
        return key == null || key.isValid();
    }


    @Override
    public GunNettyChannel<SocketChannel, GunDataEventLoop<SocketChannel>,
            GunNettyChildrenPipeline> closeAndRemove(boolean notHappenedOnRead) {
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
        destroy();
        return this;
    }


    @Override
    public GunNettyServerChannel<ServerSocketChannel> parent() {
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
            execute(() -> {
                try {
                    this.key = eventLoop.registerReadKey(channel(), this).get();
                    observes.parallelStream().forEach(v -> v.whenRegister(channel()));
                } catch (InterruptedException | ExecutionException | IOException e) {
                    observes.parallelStream().forEach(v -> v.whenRegisterMeetException(channel(), e));
                }
            });
        } catch (IOException e) {
            observes.parallelStream().forEach(v -> v.whenRegisterMeetException(channel(), e));
        }
    }


    @Override
    public void recoverReadInterest() {
        readCompleteCallBack.accept(this);
        observes.parallelStream().forEach(GunNettyChildChannelObserve::onRecoverReadInterest);
    }

    @Override
    public void sendMessage(ByteBuffer byteBuffer) throws IOException {
        this.writeBuffer.add(byteBuffer);
    }

    @Override
    public SelectionKey key() {
        return this.key;
    }

    @Override
    public void setWhenReadCompleteCallBack(Consumer<GunNettyChildChannel<SocketChannel>> t) {
        this.readCompleteCallBack = t;
    }

    @Override
    public void doTime() {
        doTck(unsafeSequencer.nextSequence());
    }

    private void doTck(long k) {
        GunNettyTimeExecutor.execute(timers, k, scheduledExecutorService, this);
    }

    @Override
    public GunNettyFilter.DealResult generalClose() {
        closeAndRemove(true);
        destroy();
        return GunNettyFilter.DealResult.CLOSED;
    }

}
