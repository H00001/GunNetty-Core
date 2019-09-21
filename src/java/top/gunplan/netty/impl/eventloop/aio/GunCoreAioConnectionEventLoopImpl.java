/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop.aio;


import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.impl.GunNettyEventLoopManager;
import top.gunplan.netty.impl.eventloop.AbstractGunCoreAioEventLoop;

import java.nio.channels.Selector;

/**
 * GunCoreAioConnectionEventLoopImpl
 *
 * @author frank albert
 * @version 0.0.0.1
 */
public class GunCoreAioConnectionEventLoopImpl extends AbstractGunCoreAioEventLoop {

    @Override
    public synchronized void run() {

    }


    @Override
    public void startEventLoop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void stopEventLoop() {

    }


    @Override
    public int listenPort() {
        return 0;
    }

    @Override
    public Selector select() {
        return null;
    }

    @Override
    public GunCoreEventLoop registerManager(GunNettyEventLoopManager manager) {
        return null;
    }
}
