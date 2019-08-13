/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.GunNettyHandle;
import top.gunplan.netty.impl.channel.GunNettyChannel;

import java.nio.channels.Channel;
import java.util.List;


/**
 * BaseGunNettyWorker real worker
 *
 * @author frank albert
 */
abstract class BaseGunNettyWorker<CH extends Channel, LOOP extends GunCoreEventLoop, PL extends GunNettyHandle> implements GunNettyWorker {
    final GunNettyChannel<CH, LOOP, PL> channel;
    final PL handle;
    final List<GunNettyFilter> filters;


    BaseGunNettyWorker(final GunNettyChannel<CH, LOOP, PL> channel) {
        this.channel = channel;
        this.handle = channel.pipeline().handel();
        this.filters = channel.pipeline().filters();
    }

}
