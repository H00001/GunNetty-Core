/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunNettyChildrenHandle;
import top.gunplan.netty.GunNettyParentHandle;
import top.gunplan.netty.filter.GunNettyConnFilter;
import top.gunplan.netty.filter.GunNettyDataFilter;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.stream.Stream;


/**
 * BaseGunNettyWorker real worker
 *
 * @author frank albert
 */
abstract class BaseGunNettyWorker implements GunNettyWorker {
    final SocketChannel javaChannel;
    final GunNettyChildrenHandle handle;
    final GunNettyParentHandle pHandle;
    final GunNettyChildChannel<SocketChannel> channel;
    final Stream<GunNettyDataFilter> dataFilterStream;
    final Stream<GunNettyConnFilter> connFilterStream;
    final List<GunNettyConnFilter> connFilters;
    final List<GunNettyDataFilter> dataFilters;


    BaseGunNettyWorker(final GunNettyChildChannel<SocketChannel> channel) {
        this.javaChannel = channel.channel();
        this.channel = channel;
        this.handle = channel.pipeline().childHandel();
        this.pHandle = channel.pipeline().parentHandel();
        this.connFilters = channel.pipeline().connFilters();
        this.dataFilters = channel.pipeline().dataFilters();
        this.dataFilterStream = channel.pipeline().dataFilterStream();
        this.connFilterStream = channel.pipeline().connFilterStream();
    }

    public boolean beforeWork() {
        return init() == 0;
    }


    public boolean endWork() {
        return destroy() == 0;
    }

    /**
     * do real work in children class
     *
     * @return work result
     */
    public abstract boolean doRealWork();

    @Override
    public void work() {
        if (beforeWork() && doRealWork()) {
            endWork();
        }

    }
}
