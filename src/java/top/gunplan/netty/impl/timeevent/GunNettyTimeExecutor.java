/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.timeevent;

import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.anno.GunTimeExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * GunNettyTimeExecutor
 *
 * @author frankHan
 */
public final class GunNettyTimeExecutor {

    public static void execute(List<GunNettyTimer> timers, long tick, ScheduledExecutorService ses, Object... objc) {
        timers.parallelStream().forEach(t -> executeOne(t, tick, ses, objc));
    }

    static void executeOne(GunNettyTimer timer, long tick, ScheduledExecutorService ses, Object... objc) {
        Arrays.stream(timer.getClass().getDeclaredMethods()).
                filter(who -> {
                    final GunTimeExecutor g = who.getAnnotation(GunTimeExecutor.class);
                    return g != null && tick % g.interval() == 0;
                })
                .parallel().forEach(who -> {
            try {
                who.invoke(timer, objc);
            } catch (ReflectiveOperationException e) {
                if (!timer.timeExecuteError(who.getName(), e)) {
                    ses.shutdown();
                }
            }
        });
    }
}
