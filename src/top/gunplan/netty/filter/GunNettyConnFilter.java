/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.filter;

import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.nio.channels.SocketChannel;

/**
 * GunNettyConnFilter
 *
 * @author frank albert
 * @version 0.0.0.2
 */
public interface GunNettyConnFilter extends GunNettyFilter {

    /**
     * doConnFilter
     *
     * @param ch channel
     * @return is or not continue
     */
    DealResult doConnFilter(GunNettyChildChannel<SocketChannel> ch);

}
