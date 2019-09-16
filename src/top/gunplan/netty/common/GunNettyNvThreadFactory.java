package top.gunplan.netty.common;

import java.util.concurrent.ThreadFactory;

/**
 * GunNettyNvThreadFactory
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-06-30 09:49
 */
public interface GunNettyNvThreadFactory extends ThreadFactory {
    /**
     * newThread
     *
     * @param r runnable @see ThreadFactory
     * @return Thread ThreadFactory
     * @see ThreadFactory
     */
    @Override
    Thread newThread(Runnable r);

    /**
     * newThread
     *
     * @param r       runnable @see ThreadFactory
     * @param handler handler to deal exception
     * @return Thread ThreadFactory
     * @see ThreadFactory
     */

    Thread newThread(Runnable r, Thread.UncaughtExceptionHandler handler);
}
