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
 * # 2019-06-19 00:38
 */
public final class GunNettyExecutors {

    private static final BlockingQueue<Runnable> SYNC_INST = new SynchronousQueue<>();

    private static final short ONE = 1;

    public static ExecutorService newNoQueueFixedExecutorPool(int size) {
        return newNoQueueFixedExecutorPool(size, GunNettyExecutors.class.getName());
    }

    public static ExecutorService newNoQueueFixedExecutorPool(int size, boolean isSteal) {
        return isSteal ? Executors.newWorkStealingPool(size) : newNoQueueFixedExecutorPool(size);
    }

    public static ExecutorService newNoQueueFixedExecutorPool(int size, String name) {
        return new ThreadPoolExecutor(size, size, 0, TimeUnit.MILLISECONDS, SYNC_INST, new GunNettyThreadFactory(name));
    }

    public static ExecutorService newFixedCacheableExecutorPool(int size, String name, boolean isSteal) {
        return isSteal ? Executors.newWorkStealingPool(size) : new ThreadPoolExecutor(size, size, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(size), new GunNettyThreadFactory(name));
    }

    public static ExecutorService newSignalExecutorPool(String name) {
        return newNoQueueFixedExecutorPool(ONE, name);
    }

    public static ScheduledExecutorService newScheduleExecutorPool(int num) {
        return newScheduleExecutorPool(num, "GunNetty-Auto-Schedule-Executor-52");
    }

    public static ScheduledExecutorService newScheduleExecutorPool(int num, String name) {
        return num <= 0 ? null : new ScheduledThreadPoolExecutor(num, new GunNettyThreadFactory(name));
    }

    public static void executeByNewThread(Runnable runner) {
        new Thread(runner, "TEMP THREAD").start();
    }

    public static ScheduledExecutorService newScheduleExecutorPool() {
        return newScheduleExecutorPool(1, "Signal-Auto-Schedule-Executor-78");
    }

}
