/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;


import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.impl.GunNettyChannel;
import top.gunplan.netty.impl.GunNettyChannelTransfer;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * GunNettyBaseTransferEventLoopImpl
 *
 * @author frank albert
 * @version 0.0.0.e
 * @date 2019-06-09 22:10
 */
class GunNettyBaseTransferEventLoopImpl<U extends SocketChannel> extends AbstractGunTransferEventLoop<U> {

    private final BlockingQueue<GunNettyChannel<U>> kQueue = new LinkedBlockingQueue<>();


    @Override
    public void loopTransfer() {
        try {
            GunNettyChannel<U> socketChannel = kQueue.take();
            final SelectionKey key = registerReadChannelToDataEventLoop(socketChannel);
            dealEvent(key);
        } catch (IOException e) {
            GunNettyContext.logger.error(e);
        } catch (InterruptedException ignored) {

        }
    }

    @Override
    public void push(GunNettyChannelTransfer<GunNettyChannel<U>> u) {
        kQueue.offer(u.channel());
    }
}
