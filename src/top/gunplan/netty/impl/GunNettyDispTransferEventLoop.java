package top.gunplan.netty.impl;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import top.gunplan.netty.common.GunNettyThreadFactory;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class GunNettyDispTransferEventLoop<U extends SocketChannel> implements GunNettyTransfer<U>, EventHandler<GunNettyChannelTransfer<SocketChannel>> {
    private final Disruptor<GunNettyChannelTransferImpl> disruptor;
    private int bufferSize = 1024;

    {
        disruptor = new Disruptor<>(GunNettyChannelTransferImpl::new, bufferSize, new GunNettyThreadFactory(this.getClass().getName()), ProducerType.MULTI, new BlockingWaitStrategy());
        disruptor.start();
    }

    @Override
    public void onEvent(GunNettyChannelTransfer<SocketChannel> event, long sequence, boolean endOfBatch) throws Exception {
        SocketChannel socketChannel = event.getChannel();
        GunCoreDataEventLoop selectionThread = ((GunCoreDataEventLoop) GunNettyCoreThreadManage.getDealThread());
        socketChannel.socket().setTcpNoDelay(true);
        socketChannel.configureBlocking(false);
        final SelectionKey key = selectionThread.registerReadKey(socketChannel);
        dealEvent(key);
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
    public void dealEvent(SelectionKey key) throws Exception {

    }

    @Override
    public void run() {
        loop();
    }
}
