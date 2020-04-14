/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel.pool;

import top.gunplan.netty.impl.channel.GunNettyChannel;

/**
 * GunNettyChannelPool
 *
 * @author frank albert
 * #date 2019-09-18 09:05
 * @version 0.0.0.2
 */
public interface GunNettyChannelPool {
    GunChildrenChannelPool INSTANCE = new GunChildrenChannelPoolImpl();

    /**
     * acquireChannelFromPool
     *
     * @return pool
     */
    GunNettyChannel acquireChannelFromPool();

    /**
     * releaseChannelToPool
     *
     * @param channel GunNettyChannel
     */
    void releaseChannelToPool(GunNettyChannel channel);
}
