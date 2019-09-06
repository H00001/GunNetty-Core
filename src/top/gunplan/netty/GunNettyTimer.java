/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

/**
 * @author dosdrtt
 * @apiNote 0.1.0.3
 * this is a doTime execute interface
 * you can use this with {@link GunNettyTimer}
 */
public interface GunNettyTimer extends GunHandle {
    /**
     * ifKeyEmptyExec
     *
     * @return is or not exec
     */
    default boolean ifKeyEmptyExec() {
        return true;
    }

    /**
     * timeExecuteError
     *
     * @param methodName timer event
     * @param t          ReflectiveOperationException
     * @return next execute
     */
    boolean timeExecuteError(String methodName, ReflectiveOperationException t);

}
