/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop.aio;


import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.impl.GunNettyEventLoopManager;
import top.gunplan.netty.impl.eventloop.AbstractGunCoreAioEventLoop;

import java.nio.channels.Selector;

/**
 * GunCoreAioDataEventLoopImpl
 *
 * @author frank albert
 * @version 0.0.0.2
 * # 2019-07-27 08:09
 */
public class GunCoreAioDataEventLoopImpl extends AbstractGunCoreAioEventLoop {


    @Override
    public void loop() {

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
