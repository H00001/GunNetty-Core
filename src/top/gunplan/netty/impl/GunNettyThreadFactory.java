package top.gunplan.netty.impl;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author dosdrtt
 */
public class GunNettyThreadFactory implements ThreadFactory {
    private String poolName;
    private LongAdder haveUsedCount = new LongAdder();

    GunNettyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        haveUsedCount.increment();
        Thread t = new Thread(r, poolName + "-" + haveUsedCount.longValue());
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        t.setPriority(9);
        return t;
    }
}
