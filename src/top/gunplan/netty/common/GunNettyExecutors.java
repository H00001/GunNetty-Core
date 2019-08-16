/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.common;

import java.util.concurrent.*;

/**
 * GunNettyExecutors
 *
 * @author frank albert
 * @version 0.0.0.3
 * @date 2019-06-19 00:38
 */
public final class GunNettyExecutors {

    private static final BlockingQueue<Runnable> SYNC_INST = new SynchronousQueue<>();

    private static final short ONE = 1;

    public static ExecutorService newFixedExecutorPool(int size) {
        return newFixedExecutorPool(size, GunNettyExecutors.class.getName());
    }

    public static ExecutorService newFixedExecutorPool(int size, String name) {
        return new ThreadPoolExecutor(size, size, 0, TimeUnit.MILLISECONDS, SYNC_INST, new GunNettyThreadFactory(name));
    }

    public static ExecutorService newSignalExecutorPool(String name) {
        return newFixedExecutorPool(ONE, name);
    }

    private static ScheduledExecutorService newScheduleExecutorPool(int num) {
        return new ScheduledThreadPoolExecutor(num, new GunNettyThreadFactory("GunNettyExecutors"));
    }


    public static ScheduledExecutorService newScheduleExecutorPool() {
        return newScheduleExecutorPool(1);
    }

}
