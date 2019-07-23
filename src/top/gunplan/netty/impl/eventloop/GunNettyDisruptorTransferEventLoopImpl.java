package top.gunplan.netty.impl.eventloop;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import top.gunplan.netty.common.GunNettyThreadFactory;
import top.gunplan.netty.impl.GunNettyChannelTransfer;

import java.nio.channels.SocketChannel;

/**
 * GunNettyDisruptorTransferEventLoopImpl
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-23 08:57
 */

class GunNettyDisruptorTransferEventLoopImpl<U extends SocketChannel> extends AbstractGunTransferEventLoop<U> implements EventHandler<GunNettyChannelTransfer<U>> {
    private final Disruptor<GunNettyChannelTransferImpl> disruptor;
    private int bufferSize = 1024;

    {
        disruptor = new Disruptor<>(GunNettyChannelTransferImpl::new, bufferSize, new GunNettyThreadFactory(this.getClass().getName()), ProducerType.MULTI, new BlockingWaitStrategy());
        disruptor.start();
    }


    private void publishChannel(U channel) {
        RingBuffer<GunNettyChannelTransferImpl> ringBuffer = disruptor.getRingBuffer();
        long l = ringBuffer.next();
        try {
            GunNettyChannelTransfer<SocketChannel> transfer = ringBuffer.get(l);
            transfer.setChannel(channel);
        } finally {
            ringBuffer.publish(l);
        }
    }

    @Override
    public void push(U u) {
        publishChannel(u);
    }

    @Override
    public void loop() {
        disruptor.start();
    }



    @Override
    public void onEvent(GunNettyChannelTransfer<U> event, long l, boolean b) throws Exception {
        final U socketChannel = event.getChannel();
        dealEvent(registerReadChannelToDataEventLoop(socketChannel));
    }
}
