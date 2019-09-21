/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.timeevent;

import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.anno.GunTimeExecutor;

import java.util.Arrays;
import java.util.List;

/**
 * GunNettyTimeExecutor
 *
 * @author frankHan
 */
public final class GunNettyTimeExecutor {
    public static void execute(List<GunNettyTimer> timers, long tick, Object... objc) {
        timers.parallelStream().forEach(t -> executeOne(t, tick, objc));
    }

    static void executeOne(GunNettyTimer timer, long tick, Object... objc) {
        Arrays.stream(timer.getClass().getDeclaredMethods()).
                filter(who -> {
                    final GunTimeExecutor g = who.getAnnotation(GunTimeExecutor.class);
                    return g != null && tick % g.interval() == 0;
                })
                .forEach(who -> {
                    try {
                        who.invoke(timer, objc);
                    } catch (ReflectiveOperationException e) {
                        timer.timeExecuteError(who.getName(), e);
                    }
                });
    }
}
