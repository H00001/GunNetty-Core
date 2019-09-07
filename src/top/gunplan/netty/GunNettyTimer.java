/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

/**
 * @author dosdrtt
 * @apiNote 0.1.0.4
 * this is a doTime execute interface
 * you can use this with {@link GunNettyTimer}
 */
public interface GunNettyTimer extends GunHandle {
    /**
     * timeExecuteError
     *
     * @param methodName timer event
     * @param t          ReflectiveOperationException
     * @return next execute
     */
    boolean timeExecuteError(String methodName, ReflectiveOperationException t);

}
