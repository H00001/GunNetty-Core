/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

/**
 * GunNettyCoreThreadManagerHelper
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-12 10:03
 */
public interface GunNettyCoreThreadManagerHelper {
    /**
     * create a new helper instance
     *
     * @param num sum data thread
     * @return instance
     */
    static GunNettyCoreThreadManagerHelper newInstance(int num) {
        return new CoreThreadManagerHelperImpl(num);
    }

    /**
     * acceptExecutorServices
     *
     * @return ExecutorService
     */
    ExecutorService acceptExecutorServices();

    /**
     * transferExecutorServices
     *
     * @return ExecutorService
     */
    ExecutorService transferExecutorServices();

    /**
     * timeExecutorServices
     *
     * @return ScheduledExecutorService
     */
    ScheduledExecutorService timeExecutorServices();

    /**
     * dataExecutorServices
     *
     * @return ExecutorService
     */
    ExecutorService dataExecutorServices();


    /**
     * allOfServices
     *
     * @return ExecutorService list
     */
    ExecutorService[] allOfServices();


    /**
     * submitTransfer eventloop
     *
     * @param run task
     * @return result
     */
    Future<Integer> submitTransfer(Runnable run);


    /**
     * submit data eventloop
     *
     * @param run task
     */
    void submitData(Runnable[] run);

    /**
     * submit connection eventloop
     *
     * @param run task
     * @return running result
     */
    Future<Integer> submitConnection(Runnable run);


    /**
     * submitSchedule
     *
     * @param task task
     * @param v1   interval doTime
     * @param v2   first init doTime
     */
    void submitSchedule(Runnable task, long v1, long v2);


    /**
     * shutdown pool and return right away
     *
     * @return GunNettyCoreThreadManagerHelper chain style
     */
    GunNettyCoreThreadManagerHelper shutdownReturn();


    void syncStop();


}
