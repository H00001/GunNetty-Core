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
 * @version 0.0.0.3
 */

public interface GunNettyChildrenHandle extends GunNettyHandle {
    /**
     * useToExecute
     * is or not use it to execute
     * @param in inbound
     * @return is or not execute
     */
    default boolean useToExecute(GunNetInbound in) {
        return true;
    }

    /**
     * dealDataEvent
     *
     * @param inbound GunNetInbound
     * @return GunNetOutbound
     * @throws GunException kinds of exception
     */
    GunNetOutbound dealDataEvent(GunNetInbound inbound) throws GunException;
}
