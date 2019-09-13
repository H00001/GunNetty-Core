/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.protocol.GunNetInbound;
import top.gunplan.netty.protocol.GunNetOutbound;

/**
 * GunNettyChildrenHandle
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-13 09:37
 */

public interface GunNettyChildrenHandle extends GunNettyHandle {
    /**
     * dealDataEvent
     *
     * @param inbound GunNetInbound
     * @return GunNetOutbound
     * @throws GunException kinds of exception
     */
    GunNetOutbound dealDataEvent(GunNetInbound inbound) throws GunException;
}
