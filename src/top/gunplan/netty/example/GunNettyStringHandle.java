/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.example;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunException;
import top.gunplan.netty.GunNettyChildrenHandle;
import top.gunplan.netty.protocol.GunNetInbound;
import top.gunplan.netty.protocol.GunNetOutbound;


/**
 * GunNettyStringHandle
 * a example
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-27 08:08
 */
public class GunNettyStringHandle implements GunNettyChildrenHandle {
    @Override
    public GunNetOutbound dealDataEvent(GunNetInbound request) throws GunException {
        return (GunNetOutbound) request;
    }

    @Override
    public void dealCloseEvent() {

    }

    @Override
    public void dealExceptionEvent(GunChannelException exp) {

    }
}
