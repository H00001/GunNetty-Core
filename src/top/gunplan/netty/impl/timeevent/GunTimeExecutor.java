/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.timeevent;

import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.impl.GunNettyManagerGetter;

import java.util.List;

/**
 * @author dosdrtt
 */

public interface GunTimeExecutor extends Runnable, GunNettyManagerGetter<GunTimeExecutor> {


    /**
     * run
     */
    @Override
    default void run() {
        for (; loop(); ) {
        }

    }

    /**
     * loop
     *
     * @return next loop
     */
    boolean loop();

    /**
     * registerWorker
     * <p>
     * register Worker
     *
     * @param works list of work
     * @return chain style
     */
    GunTimeExecutor registerWorker(List<GunNettyTimer> works);

    /**
     * register work
     *
     * @param work work
     * @return this chain style
     */
    GunTimeExecutor registerWorker(GunNettyTimer work);

    /**
     * delete worker form doTime execute
     *
     * @param work to erase
     * @return this chain style
     */
    GunTimeExecutor eraserWorker(GunNettyTimer work);


    /**
     * stop loop
     */
    void shutdown();
}
