/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.common;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author dosdrtt
 */
public class GunNettyThreadFactory implements GunNettyNvThreadFactory {
    private String poolName;
    private LongAdder haveUsedCount = new LongAdder();

    public GunNettyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return createThread(r, null);
    }

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    private Thread createThread(Runnable r, Thread.UncaughtExceptionHandler handler) {
        haveUsedCount.increment();
        @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
        Thread t = new Thread(r, poolName + "-" + haveUsedCount.longValue());
        if (handler != null) {
            t.setUncaughtExceptionHandler(handler);
        }
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        t.setPriority(9);
        return t;
    }

    @Override
    public Thread newThread(Runnable r, Thread.UncaughtExceptionHandler handler) {
        return createThread(r, handler);
    }
}
