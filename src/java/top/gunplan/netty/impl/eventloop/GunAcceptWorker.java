/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.filter.GunNettyConnFilter;
import top.gunplan.netty.filter.GunNettyFilter;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;
import top.gunplan.netty.impl.checker.GunNetServerOutboundChecker;
import top.gunplan.netty.impl.checker.GunOutboundChecker;
import top.gunplan.netty.protocol.GunNetOutbound;

import java.nio.channels.SocketChannel;
import java.util.ListIterator;

/**
 * GunAcceptWorker
 *
 * @author dosdrtt
 * # 2019-04-25
 */
public final class GunAcceptWorker extends BaseGunNettyWorker implements Runnable {

    GunAcceptWorker(final GunNettyChildChannel<SocketChannel> l) {
        super(l);
    }


    @Override
    public boolean doRealWork() {
        GunNetOutbound outbound = null;
        try {
            outbound = pHandle.dealConnEvent(channel.remoteAddress());
        } catch (GunChannelException e) {
            if (handle.dealExceptionEvent(e) != GunNettyFilter.DealResult.NEXT) {
                return false;
            }
        }
        GunOutboundChecker checker = new GunNetServerOutboundChecker(outbound, channel);
        ListIterator<GunNettyConnFilter> iterator = connFilters.listIterator(connFilters.size());
        GunNettyFilter.DealResult result;
        do {
            try {
                result = iterator.previous().doOutputFilter(checker);
            } catch (GunChannelException e) {
                result = handle.dealExceptionEvent(e);
            }
        }
        while (iterator.hasPrevious() && (result != GunNettyFilter.DealResult.NEXT));
        return true;
    }
}
