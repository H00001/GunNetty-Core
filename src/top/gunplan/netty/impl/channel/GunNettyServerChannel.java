/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.GunNettyParentHandle;
import top.gunplan.netty.impl.eventloop.GunConnEventLoop;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;

/**
 * GunNettyServerChannel
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-09 22:54
 */
public interface GunNettyServerChannel<CH extends Channel> extends GunNettyChannel<CH, GunConnEventLoop, GunNettyParentHandle> {
    ServerSocketChannel bind(int... port) throws IOException;


}
