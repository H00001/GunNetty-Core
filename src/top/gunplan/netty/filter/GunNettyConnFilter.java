/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.filter;

import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.nio.channels.SocketChannel;

/**
 * GunNettyConnFilter
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-18 11:40
 */
public interface GunNettyConnFilter extends GunNettyFilter {

    /**
     * doConnFilter
     *
     * @param ch channel
     * @return is or not continue
     * @apiNote 0.1.2.3
     */
    DealResult doConnFilter(GunNettyChildChannel<SocketChannel> ch);

}
