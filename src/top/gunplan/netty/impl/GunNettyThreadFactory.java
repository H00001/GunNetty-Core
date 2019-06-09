package top.gunplan.netty.impl;

import java.nio.channels.AsynchronousByteChannel;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ThreadFactory;

/**
 * @author dosdrtt
 */
public class GunNettyThreadFactory implements ThreadFactory {
    private String poolName;

    public GunNettyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, poolName);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        t.setPriority(9);
        return t;
    }
}
