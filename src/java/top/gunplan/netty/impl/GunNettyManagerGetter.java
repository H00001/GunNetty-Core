/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

/**
 * GunNettyManagerGetter
 *
 * @author frank albert
 * @version 0.0.0.1
 */
public interface GunNettyManagerGetter<K> {
    /**
     * register threadManager
     *
     * @param manager threadManager
     * @return return chain
     */
    K registerManager(GunNettyEventLoopManager manager);
}
