/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.impl.GunNettyChannel;
import top.gunplan.netty.impl.GunNettyOutputFilterChecker;
import top.gunplan.netty.protocol.GunNetOutbound;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ListIterator;

/**
 * GunAcceptWorker
 *
 * @author dosdrtt
 * @date 2019-04-25
 */
public final class GunAcceptWorker extends BaseGunNettyWorker implements Runnable {


    GunAcceptWorker(final GunNettyChannel<SocketChannel> l) {
        super(l, null);

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
            ob = this.handle.dealConnEvent(channel.remoteAddress());
        } catch (IOException e) {
            handle.dealExceptionEvent(new GunChannelException(e));
        }
        ListIterator<GunNettyFilter> iterator = filters.listIterator(filters.size());
        while (iterator.hasPrevious()) {
            GunNettyFilter.DealResult result = null;
            try {
                result = iterator.previous().doOutputFilter(new GunNettyOutputFilterChecker(ob, null), channel);
            } catch (GunChannelException e) {
                handle.dealExceptionEvent(e);
            }
            if (result == GunNettyFilter.DealResult.NOT_DEAL_OUTPUT) {
                break;
            } else if (result == GunNettyFilter.DealResult.CLOSE) {
                return;
            } else if (result == GunNettyFilter.DealResult.NOT_DEAL_ALL_NEXT) {
                return;
            }

        }


    }
}
