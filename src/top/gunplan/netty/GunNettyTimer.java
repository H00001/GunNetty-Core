/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

/**
 * @author dosdrtt
 * @apiNote 0.1.0.3
 * this is a time execute interface
 * you can use this with {@link }
 */
public interface GunNettyTimer<R> extends GunHandle {
    /**
     * get run times
     *
     * @return run times
     */
    int runingTimes();

    /**
     * ifKeyEmptyExec
     *
     * @return is or not exec
     */
    default boolean ifKeyEmptyExec() {
        return true;
    }

}
