/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunExceptionType;
import top.gunplan.netty.GunNettyDataFilter;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.impl.GunInboundChecker;
import top.gunplan.netty.impl.GunNettyFunctional;
import top.gunplan.netty.impl.GunNettyInputFilterChecker;
import top.gunplan.netty.impl.GunNettyOutBoundChecker;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;
import top.gunplan.netty.protocol.GunNetOutbound;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.ListIterator;
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
        executeEvent.put(GunNettyFilter.DealResult.CLOSED, () -> -1);
        executeEvent.put(GunNettyFilter.DealResult.NEXT, () -> 1);
        executeEvent.put(GunNettyFilter.DealResult.CLOSED_WHEN_READ, () -> -1);
        executeEvent.put(GunNettyFilter.DealResult.NOT_DEAL_ALL_NEXT, () -> -1);
        executeEvent.put(GunNettyFilter.DealResult.NATALINA, () -> 0);
        executeEvent.put(GunNettyFilter.DealResult.NOT_DEAL_OUTPUT, () -> {
            GunCoreCalculatorWorker.this.notDealOutputFlag = true;
            return 0;
        });
    }


    @Override
    public void work() {
        final GunInboundChecker gunFilterObj = new GunNettyInputFilterChecker(channel);
        GunNettyFilter.DealResult result = null;
        for (final GunNettyDataFilter filter : dataFilters) {
            try {
                result = filter.doInputFilter(gunFilterObj);
            } catch (GunChannelException e) {
                this.handle.dealExceptionEvent(e);
            }
            final int exeCode = executeEvent.get(result).apply();
            if (exeCode == 0) {
                break;
            } else if (exeCode == -1) {
                return;
            } else if (exeCode == 1) {

            } else {
                throw new GunChannelException(GunExceptionType.EXC0, "why");
            }
        }
        GunNetOutbound output = null;
        try {
            output = this.handle.dealDataEvent(gunFilterObj.transferTarget());
        } catch (GunChannelException e) {
            this.handle.dealExceptionEvent(e);
        }
        GunNettyOutBoundChecker responseFilterDto = new GunNettyOutBoundChecker(output, channel);
        responseFilterDto.setChannel(gunFilterObj.channel());
        ListIterator<GunNettyDataFilter> iterator = dataFilters.listIterator(dataFilters.size());
        for (; iterator.hasPrevious() && !notDealOutputFlag; ) {
            try {
                result = iterator.previous().doOutputFilter(responseFilterDto);
            } catch (GunChannelException e) {
                this.handle.dealExceptionEvent(e);
            }
            if (result == GunNettyFilter.DealResult.NOT_DEAL_OUTPUT) {
                break;
            } else if (result == GunNettyFilter.DealResult.CLOSED) {

            } else if (result == GunNettyFilter.DealResult.NOT_DEAL_ALL_NEXT) {
                return;
            }
        }
        destroy();

    }
}
