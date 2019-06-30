package top.gunplan.netty.common;

import java.util.concurrent.ThreadFactory;

/**
 * GunNettyNvThreadFactory
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-30 09:49
 */
public interface GunNettyNvThreadFactory extends ThreadFactory {
    @Override
    Thread newThread(Runnable r);


    Thread newThread(Runnable r, Thread.UncaughtExceptionHandler handler);
}
