package top.gunplan.netty.impl;


import top.gunplan.netty.common.GunNettyContext;

import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * GunNettyBaseTransferEventLoop
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-06-09 22:10
 */
public class GunNettyBaseTransferEventLoop<U extends SocketChannel> implements GunNettyTransfer<U> {

    private final BlockingQueue<U> kQueue = new LinkedBlockingQueue<>();


    @Override
    public void dealEvent(SelectionKey socketChannel) throws SocketException {
        ((SocketChannel) socketChannel.channel()).socket().setTcpNoDelay(true);
    }

    @Override
    public void run() {
        loop();
    }

    @Override
    public void push(U u) {
        kQueue.offer(u);
    }

    @Override
    public void loop() {
        for (; GunNettyCoreThreadManage.status; ) {
            try {
                U socketChannel = kQueue.take();
                GunNettySelectionChannelRegister<SelectableChannel> register = ((GunCoreDataEventLoop) GunNettyCoreThreadManage.getDealThread());
                socketChannel.configureBlocking(false);
                final SelectionKey key = register.registerReadKey(socketChannel);
                dealEvent(key);
            } catch (InterruptedException | IOException e) {
                GunNettyContext.logger.error(e);
            }

        }

    }
}
