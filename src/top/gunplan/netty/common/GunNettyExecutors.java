package top.gunplan.netty.common;

import java.util.concurrent.*;

/**
 * GunNettyExecutors
 *
 * @author frank albert
 * @version 0.0.0.1
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
}
