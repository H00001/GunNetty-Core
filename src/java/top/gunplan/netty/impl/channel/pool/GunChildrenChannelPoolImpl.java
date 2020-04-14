/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel.pool;

import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.nio.channels.SocketChannel;

/**
 * GunChildrenChannelPoolImpl
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-12-22 12:37
 */
public final class GunChildrenChannelPoolImpl extends BaseGunChannelPool<GunNettyChildChannel<SocketChannel>> implements GunChildrenChannelPool {

    @Override
    public void release(GunNettyChildChannel<SocketChannel> channel) {
        super.release(channel);
    }


    @Override
    public void addChannelToUse(GunNettyChildChannel<SocketChannel> channel) {
        super.addChannelToPool0(channel);
    }


}
