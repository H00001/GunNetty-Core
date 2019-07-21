package top.gunplan.netty.impl.eventloop;


import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettySystemServices;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.impl.GunCoreDataEventLoop;
import top.gunplan.netty.impl.GunNettyCoreThreadManager;
import top.gunplan.netty.impl.GunNettySelectionChannelRegister;

import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * GunNettyBaseTransferEventLoopImpl
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-06-09 22:10
 */
class GunNettyBaseTransferEventLoopImpl<U extends SocketChannel> extends AbstractGunTransferEventLoop<U> {

    private final BlockingQueue<U> kQueue = new LinkedBlockingQueue<>();


    @Override
    public void dealEvent(SelectionKey socketChannel) throws SocketException {
        ((SocketChannel) socketChannel.channel()).socket().setTcpNoDelay(true);
    }


    @Override
    public void push(U u) {
        kQueue.offer(u);
    }

    @Override
    public void loop() {
        for (; isRunning(); ) {
            try {
                U socketChannel = kQueue.take();
                GunNettySelectionChannelRegister<SelectableChannel> register = ((GunCoreDataEventLoop) manager.dealChannelThread());
                socketChannel.configureBlocking(false);
                final SelectionKey key = register.registerReadKey(socketChannel);
                dealEvent(key);
            } catch (InterruptedException | IOException e) {
                GunNettyContext.logger.error(e);
            }

        }

    }
}
