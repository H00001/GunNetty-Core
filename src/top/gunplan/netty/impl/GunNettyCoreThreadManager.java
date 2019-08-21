/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.GunNettyServicesObserve;
import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.SystemChannelChangedHandle;
import top.gunplan.netty.impl.property.GunNettyCoreProperty;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * GunNettyCoreThreadManager
 *
 * @author frank albert
 * @version 0.0.h.1
 * @date 2019-07-21 15:18
 */
public interface GunNettyCoreThreadManager {
    /**
     * initInstance
     *
     * @param observe  event observe
     * @param property core property
     * @param l        timers
     * @return GunNettyCoreThreadManager
     */
    static GunNettyCoreThreadManager initInstance(final GunNettyCoreProperty property, final GunNettyServicesObserve observe, List<GunNettyTimer> l) {
        assert l != null;
        return new GunNettyCoreThreadManageImpl(property, l);
    }


    /**
     * stop all and wait for stop
     *
     * @return result
     * @throws InterruptedException when something happened
     */
    boolean stopAndWait() throws InterruptedException;


    /**
     * boot server
     *
     * @return future result
     */
    Future<Integer> startAndWait();


    /**
     * init
     *
     * @param acceptExecutor     executor to deal accept event
     * @param dataExecutor       executor to deal data event
     * @param port               listen the port
     * @param childrenInitHandle what happened when children channel init
     * @param initHandle         what happened when parent channel init
     * @return init result
     * @throws IOException i/o error
     */
    boolean init(ExecutorService acceptExecutor, ExecutorService dataExecutor, SystemChannelChangedHandle initHandle, ChannelInitHandle childrenInitHandle, int port) throws IOException;


    /**
     * running state
     *
     * @return manage state
     */
    ManagerState runState();
}

