package top.gunplan.netty.impl;

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
    void run();


}
