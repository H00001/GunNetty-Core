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
     * get interval
     *
     * @return -1 is always
     */
    int interval();


    /**
     * get run times
     *
     * @return run times
     */
    int runingTimes();


    /**
     * available keys
     *
     * @param keys selection keys set
     * @return work result
     */
    int doWork(R keys);


    /**
     * ifKeyEmptyExec
     *
     * @return is or not exec
     */
    default boolean ifKeyEmptyExec() {
        return true;
    }

}
