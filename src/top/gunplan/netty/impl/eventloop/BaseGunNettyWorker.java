/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettyHandle;
import top.gunplan.netty.impl.channel.GunNettyChannel;

import java.nio.channels.Channel;


/**
 * BaseGunNettyWorker real worker
 *
 * @author frank albert
 */
abstract class BaseGunNettyWorker<A extends Channel, B extends GunCoreEventLoop, C extends GunNettyHandle, CH extends GunNettyChannel<A, B, C>> implements GunNettyWorker {
    final A javaChannel;
    final C handle;
    final CH channel;


    BaseGunNettyWorker(final CH channel) {
        this.javaChannel = channel.channel();
        this.channel = channel;
        this.handle = channel.pipeline().handel();
        this.filters = channel.pipeline().filters();
    }

}
