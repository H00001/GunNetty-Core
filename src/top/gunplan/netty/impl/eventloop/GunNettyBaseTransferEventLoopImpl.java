/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;


import top.gunplan.netty.impl.GunNettyChannelTransfer;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;

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

    private final BlockingQueue<GunNettyChildChannel<U>> kQueue = new LinkedBlockingQueue<>();


    @Override
    public void loopTransfer() {
        try {
            GunNettyChildChannel<U> channel = kQueue.take();
            registerReadChannelToDataEventLoop(channel);
        } catch (InterruptedException ignored) {

        }
    }


    @Override
    public void push(GunNettyChannelTransfer<U> u) {
        kQueue.offer(u.channel());
    }
}
