/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.impl.GunNettyManagerGetter;
import top.gunplan.netty.impl.eventloop.GunNettyVariableWorker;
import top.gunplan.netty.impl.property.GunNettyCoreProperty;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutorService;

/**
 * GunCoreEventLoop event loop deal class
 * when event happened, function `dealEvent` will be
 * execute
 *
 * @author dosdrtt
 */

public interface GunCoreEventLoop extends Runnable, GunNettyVariableWorker, GunNettyManagerGetter<GunCoreEventLoop> {
    GunNettyCoreProperty GUN_NETTY_CORE_PROPERTY = GunNettySystemServices.coreProperty();

    /**
     * when the event come ,the method will be call back
     *
     * @param key SelectionKey input the SelectionKey
     * @throws Exception Kinds of Exception
     */
    void dealEvent(SelectionKey key) throws Exception;


    /**
     * running on new thread not main
     */
    @Override
    default void run() {
        startEventLoop();
        loop();
    }


    /**
     * loop loop
     */
    void loop();


    int fastLimit() throws IOException;


    /**
     * isLoopNext
     *
     * @return is or not loop
     */
    boolean isLoopNext();


    /**
     * available channels
     *
     * @param deal deal event Executor
     * @return int init result
     * @throws IOException some i/o can not opened
     */
    int init(final ExecutorService deal) throws IOException;
}
