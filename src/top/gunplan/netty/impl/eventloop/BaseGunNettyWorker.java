/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.GunNettyHandle;
import top.gunplan.netty.impl.GunNettyChannel;

import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * BaseGunNettyWorker real worker
 *
 * @author frank albert
 */
abstract class BaseGunNettyWorker implements GunNettyWorkerInterface {
    final GunNettyChannel<SocketChannel> channel;
    final GunNettyHandle handle;
    final List<GunNettyFilter> filters;
    private final AtomicInteger waitSize;


    BaseGunNettyWorker(final GunNettyChannel<SocketChannel> channel, final AtomicInteger waitSize) {
        this.channel = channel;
        this.handle = channel.pipeline().handel();
        this.filters = channel.pipeline().filters();
        this.waitSize = waitSize;

    }


    @Override
    public int decreaseChannel(int sum) {
        if (waitSize != null) {
            for (; ; ) {
                final int nowValue = waitSize.get();
                final int except = nowValue - sum;
                if (this.waitSize.compareAndExchangeRelease(nowValue, except) == except) {
                    return except;
                }
            }
        }
        return -32768;
    }

}
