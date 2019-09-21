/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelTransfer
 *
 * @author frank albert
 * @version 0.0.0.2
 * # 2019-07-17 07:39
 */
public interface GunNettyChannelTransfer<U extends SocketChannel> {
    /**
     * transferTarget's channel
     *
     * @return channel
     */
    GunNettyChildChannel<U> channel();

    /**
     * set channel
     *
     * @param channel set channel
     */
    void setChannel(GunNettyChildChannel<U> channel);


    /**
     * get the key
     *
     * @return key unique identifier
     */
    long key();

}
