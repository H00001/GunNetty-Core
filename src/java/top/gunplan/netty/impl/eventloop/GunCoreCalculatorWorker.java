/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.filter.GunNettyDataFilter;
import top.gunplan.netty.filter.GunNettyFilter;
import top.gunplan.netty.impl.GunNettyFunctional;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;
import top.gunplan.netty.impl.checker.GunInboundChecker;
import top.gunplan.netty.impl.checker.GunNetServerInboundChecker;
import top.gunplan.netty.impl.checker.GunNetServerOutboundChecker;
import top.gunplan.netty.protocol.GunNetOutbound;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;


/**
 * @author dosdrtt
 */

public final class GunCoreCalculatorWorker extends
        BaseGunNettyWorker {
    private final Map<GunNettyFilter.DealResult, GunNettyFunctional> executeEvent = new HashMap<>(5);
    private boolean notDealOutputFlag = false;


    GunCoreCalculatorWorker(final GunNettyChildChannel<SocketChannel> nettyChannel) {
        super(nettyChannel);
        executeEvent.put(GunNettyFilter.DealResult.CLOSED, () -> {
            handle.dealCloseEvent(nettyChannel.remoteAddress());
            return -1;
        });
        executeEvent.put(GunNettyFilter.DealResult.NEXT, () -> 1);
        executeEvent.put(GunNettyFilter.DealResult.NOT_DEAL_ALL_NEXT, () -> -1);
        executeEvent.put(GunNettyFilter.DealResult.NATALINA, () -> 0);
        executeEvent.put(GunNettyFilter.DealResult.NOT_DEAL_OUTPUT, () -> {
            this.notDealOutputFlag = true;
            return 0;
        });
    }

    //todo
    @Override
    public boolean doRealWork() {
        final GunInboundChecker inbound = new GunNetServerInboundChecker(channel);
        GunNettyFilter.DealResult result = null;
        for (final GunNettyDataFilter filter : dataFilters) {
            try {
                result = filter.doInputFilter(inbound);
            } catch (GunChannelException e) {
                this.handle.dealExceptionEvent(e);
            }
            final int exeCode = executeEvent.get(result).apply();
            if (exeCode == 0) {
                break;
            } else if (exeCode == -1) {
                return false;
            }
        }
        GunNetOutbound output = doNetOutbound(inbound);
        GunNetServerOutboundChecker outboundChecker = new GunNetServerOutboundChecker(output, channel);
        for (int i = dataFilters.size() - 1; i >= 0 && !notDealOutputFlag; i--) {
            try {
                result = dataFilters.get(i).doOutputFilter(outboundChecker);
            } catch (GunChannelException e) {
                this.handle.dealExceptionEvent(e);
            }
            if (result == GunNettyFilter.DealResult.NOT_DEAL_OUTPUT || result == GunNettyFilter.DealResult.NOT_DEAL_ALL_NEXT) {
                break;
            } else if (result == GunNettyFilter.DealResult.CLOSED) {
                return false;
            }
        }
        return true;
    }


    private GunNetOutbound doNetOutbound(GunInboundChecker inbound) {
        GunNetOutbound output = null;
        try {
            output = handle.dealDataEvent(inbound.transferTarget());
        } catch (GunChannelException e) {
            this.handle.dealExceptionEvent(e);
        }
        return output;
    }
}
