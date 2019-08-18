/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunNettyConnFilter;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.GunNettyParentHandle;
import top.gunplan.netty.impl.GunNettyOutputFilterChecker;
import top.gunplan.netty.impl.channel.GunNettyServerChannel;
import top.gunplan.netty.protocol.GunNetOutbound;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.util.List;
import java.util.ListIterator;

/**
 * GunAcceptWorker
 *
 * @author dosdrtt
 * @date 2019-04-25
 */
public final class GunAcceptWorker extends BaseGunNettyWorker<ServerSocketChannel, GunConnEventLoop, GunNettyParentHandle,
        GunNettyServerChannel<ServerSocketChannel>> implements Runnable {

    final List<GunNettyConnFilter> filters;

    GunAcceptWorker(final GunNettyServerChannel<ServerSocketChannel> l) {
        super(l);
        this.filters = l.pipeline().filters();
    }


    @Override
    public void work() {
        for (GunNettyFilter filter : filters) {
            if (!filter.doConnFilter(channel)) {
                return;
            }
        }
        GunNetOutbound ob = null;
        try {
            ob = this.channel.remoteAddress();
        } catch (IOException e) {
            handle.dealExceptionEvent(new GunChannelException(e));
        }
        ListIterator<GunNettyFilter> iterator = filters.listIterator(filters.size());
        GunNettyFilter.DealResult result = null;
        do {
            try {
                result = iterator.previous().doOutputFilter(new GunNettyOutputFilterChecker(ob, javaChannel));
            } catch (GunChannelException e) {
                handle.dealExceptionEvent(e);
            }
        }
        while (iterator.hasPrevious() && (result != GunNettyFilter.DealResult.NEXT))


    }
}
