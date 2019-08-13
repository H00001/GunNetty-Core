/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

/**
 * net handle is a handle interface used to deal event
 *
 * @author dosdrtt
 */
public interface GunNettyHandle extends GunHandle {

    /**
     * when close event happened ,the method will be called
     */
    void dealCloseEvent();

    /**
     * when exception event happened ,the method will be called
     *
     * @param exp Exception
     */
    void dealExceptionEvent(GunChannelException exp);
}
