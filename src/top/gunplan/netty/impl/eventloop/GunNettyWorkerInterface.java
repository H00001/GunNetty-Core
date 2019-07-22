package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunHandle;

/**
 * GunNettyWorkerInterface
 *
 * @author dosdrtt
 * @see java.lang.Runnable
 */
@FunctionalInterface
interface GunNettyWorkerInterface extends Runnable, GunHandle {
    /**
     * run
     */
    @Override
    default void run() {
        work();
    }

    void work();

}
