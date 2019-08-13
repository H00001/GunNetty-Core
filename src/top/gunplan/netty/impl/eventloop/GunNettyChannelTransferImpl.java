/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.impl.GunNettyChannelTransfer;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelTransferImpl
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-06-19 00:02
 */

public class GunNettyChannelTransferImpl<U extends SocketChannel> implements
        GunNettyChannelTransfer<U> {
    private volatile GunNettyChildChannel<U> channel;

    GunNettyChannelTransferImpl(GunNettyChildChannel<U> channel) {
        this.channel = channel;
    }

    public GunNettyChannelTransferImpl() {
    }

    @Override
    public GunNettyChildChannel<U> channel() {
        return channel;
    }

    @Override
    public void setChannel(GunNettyChildChannel<U> channel) {
        this.channel = channel;
    }


    @Override
    public long key() {
        return channel.channelId();
    }
}
