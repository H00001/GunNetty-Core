package top.gunplan.netty.impl;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;

/**
 * GunNettyTransferEventLoop
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-09 22:10
 */
public class GunNettyTransferEventLoop implements GunCoreEventLoop {

    private final BlockingQueue<SocketChannel> kQueue;

    GunNettyTransferEventLoop(BlockingQueue<SocketChannel> keyQueue) {
        this.kQueue = keyQueue;
    }

    @Override
    public void dealEvent(SelectionKey socketChannel) {

    }

    @Override
    public void run() {
        try {
            for (; GunNettyCoreThreadManage.status; ) {
                SocketChannel socketChannel = kQueue.take();
                GunCoreDataEventLoop selectionThread = ((GunCoreDataEventLoop) GunNettyCoreThreadManage.getDealThread());
                socketChannel.socket().setTcpNoDelay(true);
                socketChannel.configureBlocking(false);
                final SelectionKey key = selectionThread.registerReadKey(socketChannel);
                dealEvent(key);
            }
        } catch (InterruptedException | IOException e) {
            AbstractGunBaseLogUtil.error(e);
        }
    }
}
