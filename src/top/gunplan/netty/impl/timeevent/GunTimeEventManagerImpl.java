/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.timeevent;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionType;
import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.GunTimeEventManager;
import top.gunplan.netty.anno.GunTimeExecutor;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.impl.sequence.GunNettySequencer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * GunTimeEventManagerImpl
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-09-07 12:24
 */
public class GunTimeEventManagerImpl implements GunTimeEventManager {
    private final List<GunNettyTimer> timers = new ArrayList<>(1);
    private final GunNettySequencer sequencer = GunNettySequencer.newThreadUnSafeSequencer();
    private volatile Thread first;
    private volatile ScheduledExecutorService ses;

    @Override
    public GunTimeEventManager addGlobalTimers(GunNettyTimer timer) {
        if (first == null) {
            timers.add(timer);
            first = Thread.currentThread();
        } else if (first == Thread.currentThread()) {
            timers.add(timer);
        } else {
            throw new GunException(GunExceptionType.NOT_SUPPORT, "you shouldn't add in another thread");
        }
        return this;
    }

    @Override
    public synchronized GunTimeEventManager removeGlobalTimers(GunNettyTimer timer) {
        timers.remove(timer);
        return this;
    }

    @Override
    public void loop() {
        final long now = sequencer.nextSequenceInt32WithLimit(Integer.MAX_VALUE);
        System.out.println("now:" + now);
        timers.parallelStream().forEach(who -> Arrays.stream(who.getClass().getMethods()).
                filter(w -> {
                    final GunTimeExecutor g = w.getAnnotation(GunTimeExecutor.class);
                    return g != null && (now % g.interval() == 0 || g.interval() == -1);
                })
                .forEach(m -> {
                    try {
                        m.invoke(who);
                    } catch (ReflectiveOperationException e) {
                        who.timeExecuteError(m.getName(), e);
                    }
                }));
    }

    @Override
    public int boot(long var1, long var2) {
        ses = GunNettyExecutors.newScheduleExecutorPool();
        ses.scheduleAtFixedRate(this::loop, var1, var2, TimeUnit.MILLISECONDS);
        return 0;
    }

    @Override
    public int stop() {
        ses.shutdown();
        return 0;
    }
}
