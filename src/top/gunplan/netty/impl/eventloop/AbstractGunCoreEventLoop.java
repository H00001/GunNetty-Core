/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunException;
import top.gunplan.netty.GunHandle;
import top.gunplan.netty.impl.GunNettyCoreThreadManager;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ExecutorService;

/**
 * AbstractGunCoreEventLoop
 *
 * @author dosdrtt
 * @see GunCoreDataEventLoopImpl
 * @see GunCoreConnectionEventLoopImpl
 */
abstract class AbstractGunCoreEventLoop implements GunCoreEventLoop {
    volatile ExecutorService deal;
    volatile Selector bootSelector;
    volatile GunHandle eventHandle;
    /**
     * pipe line, executor chain
     */

    private volatile boolean running;
    volatile Thread workThread;

    GunNettyCoreThreadManager manager;

    AbstractGunCoreEventLoop() {

    }

    @Override
    public int init(final ExecutorService deal) throws IOException {
        this.deal = deal;
        bootSelector = SelectorProvider.provider().openSelector();
        return 0;
    }

    @Override
    public AbstractGunCoreEventLoop registerManager(GunNettyCoreThreadManager manager) {
        this.manager = manager;
        return this;
    }


    /**
     * dealEvent
     *
     * @param key SelectionKey
     * @throws Exception unKnown Exception
     */
    @Override
    public abstract void dealEvent(SelectionKey key) throws Exception;


    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void startEventLoop() {
        workThread = Thread.currentThread();
        this.running = true;
    }

    @Override
    public void stopEventLoop() {
        this.running = false;
    }

    /**
     * how to next deal
     */
    public abstract void nextDeal();


    @Override
    public void loop() {
        for (; isLoopNext(); ) {
            nextDeal();
        }
        try {
            whenHaltDeal();
        } catch (IOException e) {
            throw new GunException(e);
        }
    }

    /**
     * when halt the deal
     */
    void whenHaltDeal() throws IOException {
        bootSelector.close();
    }


    @Override
    public boolean isLoopNext() {
        return isRunning();
    }


}



