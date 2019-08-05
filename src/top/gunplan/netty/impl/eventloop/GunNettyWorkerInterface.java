/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
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


    int decreaseChannel(int sum);


}
