/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

/**
 * @author dosdrtt
 * this is a doTime execute interface
 * you can use this with {@link GunNettyTimer}
 */
@FunctionalInterface
public interface GunNettyTimer extends GunHandle {
    /**
     * timeExecuteError
     *
     * @param methodName timer event
     * @param t          ReflectiveOperationException
     * @return is ot not next execute
     */
    boolean timeExecuteError(String methodName, ReflectiveOperationException t);
}
