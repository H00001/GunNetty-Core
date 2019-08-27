/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.nio.channels.SocketChannel;

/**
 * GunNettyChildTimer
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-27 22:32
 */

public interface GunNettyChildTimer extends GunNettyTimer<GunNettyChildChannel<SocketChannel>> {

}
