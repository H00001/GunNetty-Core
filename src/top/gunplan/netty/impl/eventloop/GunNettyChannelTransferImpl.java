/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.impl.GunNettyChannel;
import top.gunplan.netty.impl.GunNettyChannelTransfer;

import java.nio.channels.Channel;

/**
 * GunNettyChannelTransferImpl
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-06-19 00:02
 */

public class GunNettyChannelTransferImpl<U extends Channel> implements GunNettyChannelTransfer<GunNettyChannel<U>> {
    private volatile GunNettyChannel<U> channel;

    GunNettyChannelTransferImpl(GunNettyChannel<U> channel) {
        this.channel = channel;
    }

    public GunNettyChannelTransferImpl() {
    }

    @Override
    public GunNettyChannel<U> channel() {
        return channel;
    }

    @Override
    public void setChannel(GunNettyChannel<U> channel) {
        this.channel = channel;
    }


    @Override
    public long key() {
        return channel.channelId();
    }
}
