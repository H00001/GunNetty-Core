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
 * @version 0.0.0.1
 * @date 2019-09-07 12:12
 */
public class GlobalTimer implements GunNettyTimer {


    @Override
    public boolean timeExecuteError(String methodName, ReflectiveOperationException t) {
        return false;
    }

    @GunTimeExecutor(interval = -1, t = @GunHandleTag(id = 10, name = "gt"))
    public void doTime() {
        System.out.println("timing ");
    }
}
