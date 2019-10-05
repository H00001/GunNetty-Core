/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel.pool;

import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.nio.channels.SocketChannel;

final class GunChildrenChannelPoolImpl extends BaseGunChannelPool<GunNettyChildChannel<SocketChannel>> implements GunChildrenChannelPool {

    @Override
    public void declareNotUse(GunNettyChildChannel<SocketChannel> channel) {
        super.declareNotUse(channel);
    }

    @Override
    public boolean addChannelToPool(GunNettyChildChannel<SocketChannel> channel) {
        return super.addChannelToPool0(channel);
    }

    @Override
    protected GunNettyChildChannel<SocketChannel> acquireCreateNew() {
        return null;
    }
}
