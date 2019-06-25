package top.gunplan.netty.impl;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import top.gunplan.netty.common.GunNettyThreadFactory;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.lmax.disruptor.*;

/**
 * GunNettyTransferEventLoop
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-09 22:10
 */
public class GunNettyTransferEventLoop<U extends SocketChannel> implements GunNettyTransfer<U>, EventHandler<GunNettyChannelTransfer> {

    private final BlockingQueue<U> kQueue = new LinkedBlockingQueue<>();
    private final Disruptor<GunNettyChannelTransfer> disruptor;
    private int bufferSize = 1024;

    {
        disruptor = new Disruptor<>(GunNettyChannelTransfer::new, bufferSize, new GunNettyThreadFactory(this.getClass().getName()), ProducerType.MULTI, new BlockingWaitStrategy());
        disruptor.start();
    }


    public void publishChannel(U channel) {
        RingBuffer<GunNettyChannelTransfer> ringBuffer = disruptor.getRingBuffer();
        long l = ringBuffer.next();
        try {
            GunNettyChannelTransfer transfer = ringBuffer.get(l);
            transfer.setChannel(channel);
        } finally {
            ringBuffer.publish(l);
        }
    }

    @Override
    public void dealEvent(SelectionKey socketChannel) throws SocketException {
        ((SocketChannel) socketChannel.channel()).socket().setTcpNoDelay(true);
    }

    @Override
    public void run() {
        try {
            for (; GunNettyCoreThreadManage.status; ) {
                U socketChannel = kQueue.take();
                GunCoreDataEventLoop selectionThread = ((GunCoreDataEventLoop) GunNettyCoreThreadManage.getDealThread());
                socketChannel.configureBlocking(false);
                final SelectionKey key = selectionThread.registerReadKey(socketChannel);
                dealEvent(key);
            }
        } catch (InterruptedException | IOException e) {
            AbstractGunBaseLogUtil.error(e);
        }
    }

    @Override
    public void onEvent(GunNettyChannelTransfer event, long sequence, boolean endOfBatch) throws Exception {
        SocketChannel socketChannel = event.getChannel();
        GunCoreDataEventLoop selectionThread = ((GunCoreDataEventLoop) GunNettyCoreThreadManage.getDealThread());
        socketChannel.socket().setTcpNoDelay(true);
        socketChannel.configureBlocking(false);
        final SelectionKey key = selectionThread.registerReadKey(socketChannel);
        dealEvent(key);
    }

    @Override
    public Queue<U> kQueue() {
        return kQueue;
    }
}
