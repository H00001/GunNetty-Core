/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;


import top.gunplan.netty.protocol.GunNetOutbound;

import java.net.SocketAddress;

/**
 * GunNettyParentHandle
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-13 09:36
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
