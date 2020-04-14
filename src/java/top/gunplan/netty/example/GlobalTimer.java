/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.example;

import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.anno.GunHandleTag;
import top.gunplan.netty.anno.GunTimeExecutor;

/**
 * GlobalTimer
 *
 * @author frank albert
 * @version 0.0.0.2
 */
public class GlobalTimer implements GunNettyTimer {

    @Override
    public boolean timeExecuteError(String methodName, ReflectiveOperationException t) {
        return false;
    }

    @GunTimeExecutor(interval = -1, t = @GunHandleTag(id = 10, name = "global timber 0"))
    public void doTime() {
        System.out.println("timber tick");
    }
}
