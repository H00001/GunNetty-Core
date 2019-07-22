package top.gunplan.netty.impl.aio;

import top.gunplan.netty.GunCoreEventLoop;

import java.nio.channels.SelectionKey;

/**
 * AbstractGunCoreAioEventLoop
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-09 10:25
 */

public abstract class AbstractGunCoreAioEventLoop implements GunCoreEventLoop, Runnable {

    /**
     * aio running method
     */
    @Override
    abstract public void run();

    @Override
    public void dealEvent(SelectionKey key) throws Exception {

    }
}
