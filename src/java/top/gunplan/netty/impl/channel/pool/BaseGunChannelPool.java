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
        V v = unUsedQueue.element();
        usedQueue.add(v == null ? acquireCreateNew() : v);
        return v;
    }

    protected abstract V acquireCreateNew();

    public boolean addChannelToPool0(V channel) {
        return usedQueue.add(channel);
    }


    protected void declareNotUse(V channel) {
        if (usedQueue.remove(channel) && unUsedQueue.add(channel)) {

        }
    }
}
