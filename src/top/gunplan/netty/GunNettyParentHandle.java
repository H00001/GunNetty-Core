/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;


import top.gunplan.netty.protocol.GunNetOutbound;

import java.net.SocketAddress;

/**
 * parent handle ,do parent event
 * GunNettyParentHandle
 *
 * @author frank albert
 * @version 0.0.0.3
 */
public interface GunNettyParentHandle extends GunNettyHandle {

    /**
     * dealConnEvent connection event
     *
     * @param address request address information
     * @return GunNetOutbound
     * @throws GunException kinds of exception
     * @throws GunException IO error
     */
    GunNetOutbound dealConnEvent(SocketAddress address) throws GunException;
}
