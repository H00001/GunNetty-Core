/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel.pool;

import top.gunplan.netty.impl.channel.GunNettyChannel;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

abstract class BaseGunChannelPool<V extends GunNettyChannel> {
    private final Queue<V> usedQueue = new ConcurrentLinkedQueue<>();
    private final Queue<V> unUsedQueue = new ConcurrentLinkedQueue<>();

    public V acquireChannelFromPool() {
        return unUsedQueue.poll();
    }


    public boolean addChannelToPool0(V channel) {
        return usedQueue.add(channel);
    }


    protected void release(V channel) {
        usedQueue.remove(channel);
        unUsedQueue.add(channel);
    }


}
