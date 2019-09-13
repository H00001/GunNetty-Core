package top.gunplan.netty.common;

import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.anno.GunTimeExecutor;

import java.util.Arrays;
import java.util.List;

/**
 * GunNettyTimeExecutor
 *
 * @author frankHan
 * @date 20190913
 */
public final class GunNettyTimeExecutor {
    public static void execute(List<GunNettyTimer> timers, long tick, Object... objc) {
        timers.parallelStream().forEach(t -> Arrays.stream(t.getClass().getDeclaredMethods()).
                filter(who -> {
                    final GunTimeExecutor g = who.getAnnotation(GunTimeExecutor.class);
                    return g != null && tick % g.interval() == 0;
                })
                .forEach(who -> {
                    try {
                        who.invoke(t, objc);
                    } catch (ReflectiveOperationException e) {
                        t.timeExecuteError(who.getName(), e);
                    }
                }));
    }
}
