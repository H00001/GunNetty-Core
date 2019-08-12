/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.timeevent;

import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.impl.GunNettyManagerGetter;

import java.util.List;

/**
 * @author dosdrtt
 * @date 2019/05/30
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
     * @return
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
     * delete worker form time execute
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
