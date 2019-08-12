/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.impl.eventloop.GunConnEventLoop;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

/**
 * GunNettyServerChannel
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-09 22:54
 */
public interface GunNettyServerChannel extends GunNettyChannel<ServerSocketChannel, GunConnEventLoop> {

    ServerSocketChannel bind(int... port) throws IOException;


}
