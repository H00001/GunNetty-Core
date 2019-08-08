/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import top.gunplan.netty.common.GunNettyThreadFactory;
import top.gunplan.netty.impl.GunNettyChannel;
import top.gunplan.netty.impl.GunNettyChannelTransfer;

import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * GunNettyDisruptorTransferEventLoopImpl
 *
 * @author frank albert
 * @version 0.1.0.2
 * @date 2019-07-23 08:57
 */

final class GunNettyDisruptorTransferEventLoopImpl<U extends SocketChannel> extends AbstractGunTransferEventLoop<U> implements EventHandler<GunNettyChannelTransfer<GunNettyChannel<U>>> {
    private final static int BUFFER_SIZE = 1024;
    private final Disruptor<GunNettyChannelTransfer<GunNettyChannel<U>>> disruptor;
    private RingBuffer<GunNettyChannelTransfer<GunNettyChannel<U>>> ringBuffer;

    GunNettyDisruptorTransferEventLoopImpl() {
        disruptor = new Disruptor<>(GunNettyChannelTransferImpl::new, BUFFER_SIZE,
                new GunNettyThreadFactory(this.getClass().getName()),
                ProducerType.MULTI, new BlockingWaitStrategy());
        disruptor.handleEventsWith(this);
        ringBuffer = disruptor.getRingBuffer();
    }

    private void publishChannel(GunNettyChannel<U> channel) {
        long l = ringBuffer.next();
        try {
            GunNettyChannelTransfer<GunNettyChannel<U>> transfer = ringBuffer.get(l);
            transfer.setChannel(channel);
        } finally {
            ringBuffer.publish(l);
        }
    }


    @Override
    public void push(GunNettyChannelTransfer<GunNettyChannel<U>> u) {
        publishChannel(u.channel());
    }

    @Override
    public void loopTransfer() {
        disruptor.start();
        for (; isRunning(); ) {
            try {
                TimeUnit.SECONDS.sleep(1000);
            } catch (InterruptedException ignore) {
                break;
            }
        }
        disruptor.shutdown();
    }


    @Override
    public void onEvent(GunNettyChannelTransfer<GunNettyChannel<U>> event, long sequence, boolean endOfBatch) throws Exception {
        final GunNettyChannel<U> socketChannel = event.channel();
        dealEvent(registerReadChannelToDataEventLoop(socketChannel));

    }
}
