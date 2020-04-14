/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.filter.GunNettyFilter;
import top.gunplan.netty.impl.channel.GunNettyChannelException;

import java.net.SocketAddress;

/**
 * net handle is a handle interface used to deal event
 *
 * @author dosdrtt
 */
public interface GunNettyHandle extends GunHandle {

    /**
     * when close event happened ,the method will be called
     *
     * @param address remote address
     */
    void dealCloseEvent(SocketAddress address);

    /**
     * when exception event happened ,the method will be called
     *
     * @param exp Exception
     * @return result
     */
    GunNettyFilter.DealResult dealExceptionEvent(GunNettyChannelException exp);
}
