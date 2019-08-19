/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.impl.GunNettyEventLoopManager;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

/**
 * AbstractGunTransferEventLoop
 *
 * @author frank albert
 * @version 0.0.2.1
 * @date 2019-07-23 00:36
 */
public abstract class AbstractGunTransferEventLoop<U extends SocketChannel> implements GunNettyTransferEventLoop<U> {
    private volatile boolean running = false;
    private volatile GunNettyEventLoopManager manager;

    /**
     * do transferTarget
     */
    public abstract void loopTransfer();

    @Override
    public boolean isLoopNext() {
        return isRunning();
    }

    void registerReadChannelToDataEventLoop(GunNettyChildChannel<U> channel) {
        channel.registerEventLoop(manager.dealChannelEventLoop());
        channel.registerReadWithEventLoop();
    }


    @Override
    public int init(ExecutorService deal) {
        return 0;
    }


    @Override
    public GunNettyTransferEventLoop<U> registerManager(GunNettyEventLoopManager manager) {
        this.manager = manager;
        return this;
    }


    @Override
    public void loop() {
        for (; isLoopNext(); ) {
            loopTransfer();
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void startEventLoop() {
        running = true;
    }

    @Override
    public void stopEventLoop() {
        running = false;
    }

}
