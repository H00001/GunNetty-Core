package top.gunplan.netty.impl.eventloop;


import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.impl.GunNettySelectionChannelRegister;

import java.io.IOException;
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
    public void push(U u) {
        kQueue.offer(u);
    }

    @Override
    public void loop() {
        for (; isRunning(); ) {
            try {
                U socketChannel = kQueue.take();
                dealEvent(registerReadChannelToDataEventLoop(socketChannel));
            } catch (InterruptedException | IOException e) {
                GunNettyContext.logger.error(e);
            }

        }

    }
}
