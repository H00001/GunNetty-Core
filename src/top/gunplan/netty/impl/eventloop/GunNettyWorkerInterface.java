/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunHandle;

/**
 * GunNettyWorkerInterface
 *
 * @author dosdrtt
 * @see java.lang.Runnable
 */
interface GunNettyWorkerInterface extends Runnable, GunHandle {
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


    /**
     * decrease value of channels
     *
     * @param sum channel's sum
     * @return result
     */
    int decreaseChannel(int sum);


}
