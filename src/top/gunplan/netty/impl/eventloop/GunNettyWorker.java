/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunHandle;

/**
 * GunNettyWorker
 *
 * @author dosdrtt
 * @see java.lang.Runnable
 */
interface GunNettyWorker extends Runnable, GunHandle {
    /**
     * run
     */
    @Override
    default void run() {
        work();
    }

    /**
     * do work
     */
    void work();



}
