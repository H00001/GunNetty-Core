/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.example;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunNettyChildrenHandle;
import top.gunplan.netty.GunNettyParentHandle;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.filter.GunNettyFilter;
import top.gunplan.netty.protocol.GunNetInbound;
import top.gunplan.netty.protocol.GunNetOutbound;

import java.net.SocketAddress;


/**
 * GunNettyStringHandle
 * a example
 *
 * @author frank albert
 * @version 0.0.0.1
 * #2019-07-27 08:08
 */
public class GunNettyStringHandle implements GunNettyChildrenHandle, GunNettyParentHandle {


    @Override
    public GunNetOutbound dealDataEvent(GunNetInbound inbound) throws GunException {
        return (GunNetOutbound) inbound;
    }


    @Override
    public void dealCloseEvent(SocketAddress address) {
        GunNettyContext.logger.info(address + ":has closed");
    }

    @Override
    public GunNettyFilter.DealResult dealExceptionEvent(GunChannelException exp) {
        return GunNettyFilter.DealResult.NEXT;
    }

    @Override
    public GunNetOutbound dealConnEvent(SocketAddress address) throws GunException {
        return new GunString("welcome to system now doTime:" + System.currentTimeMillis() + "\n");
    }

    @Override
    public int init() {
        System.out.println("handle init");
        return 0;
    }
}
