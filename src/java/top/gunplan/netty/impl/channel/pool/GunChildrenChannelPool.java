/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel.pool;

import top.gunplan.netty.impl.channel.GunNettyChannel;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.nio.channels.SocketChannel;

/**
 * GunChildrenChannelPool
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-09-18 09:19
 */
public interface GunChildrenChannelPool extends GunNettyChannelPool {
    @Override
    GunNettyChildChannel<SocketChannel> acquireChannelFromPool();

    @Override
    default boolean addChannelToPool(GunNettyChannel channel) {
        return channel instanceof GunNettyChildChannel && addChannelToPool(channel);
    }


    void declareNotUse(GunNettyChildChannel<SocketChannel> channel);

    boolean addChannelToPool(GunNettyChildChannel<SocketChannel> channel);
}
