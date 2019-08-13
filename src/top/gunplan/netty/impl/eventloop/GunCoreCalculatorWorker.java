/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunExceptionType;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.impl.GunNettyFunctional;
import top.gunplan.netty.impl.GunNettyInputFilterChecker;
import top.gunplan.netty.impl.GunNettyOutputFilterChecker;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;
import top.gunplan.netty.protocol.GunNetOutbound;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author dosdrtt
 */

public final class GunCoreCalculatorWorker extends BaseGunNettyWorker {


    private final Map<GunNettyFilter.DealResult, GunNettyFunctional> executeEvent = new HashMap<>(5);
    private boolean notDealOutputFlag = false;


    GunCoreCalculatorWorker(final GunNettyChildChannel<SocketChannel> nettyChannel, AtomicInteger waitSize) {
        super(nettyChannel, waitSize);
        executeEvent.put(GunNettyFilter.DealResult.CLOSE, () -> {
            decreaseChannel(1);
            return -1;
        });
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
        final GunNettyInputFilterChecker gunFilterObj = new GunNettyInputFilterChecker(channel);
        GunNettyFilter.DealResult result = null;
        for (final GunNettyFilter filter : filters) {
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
        GunNettyOutputFilterChecker responseFilterDto = new GunNettyOutputFilterChecker(output);
        responseFilterDto.setKey(gunFilterObj.getKey());
        ListIterator<GunNettyFilter> iterator = filters.listIterator(filters.size());
        for (; iterator.hasPrevious() && !notDealOutputFlag; ) {
            try {
                result = iterator.previous().doOutputFilter(responseFilterDto);
            } catch (GunChannelException e) {
                this.handle.dealExceptionEvent(e);
            }
            if (result == GunNettyFilter.DealResult.NOT_DEAL_OUTPUT) {
                break;
            } else if (result == GunNettyFilter.DealResult.CLOSE) {
                if (decreaseChannel(1) != -32768) {
                    return;
                }
            } else if (result == GunNettyFilter.DealResult.NOT_DEAL_ALL_NEXT) {
                return;
            }
        }
        destroy();

    }
}
