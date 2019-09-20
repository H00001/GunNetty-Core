/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.*;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.impl.property.GunNettyCoreProperty;
import top.gunplan.netty.impl.property.base.GunNettyPropertyManager;
import top.gunplan.netty.impl.timeevent.GunTimeEventManagerImpl;
import top.gunplan.netty.observe.DefaultSystemChannelChangedHandle;
import top.gunplan.netty.observe.GunNettyServicesObserve;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * GunBootServer's real implement ,this class is not public
 *
 * @author Gunplan
 * @version 0.1.1.4
 * @see GunBootServer
 * @since 0.0.0.5
 */

final class GunBootServerImpl implements GunBootServer {

    private volatile SystemChannelChangedHandle changedHandle = new DefaultSystemChannelChangedHandle();

    private volatile GunNettyCoreThreadManager threadManager;

    private final GunTimeEventManager timeManager = new GunTimeEventManagerImpl();

    private volatile GunNettyServicesObserve observe;

    private volatile ExecutorService acceptExecutor;

    private volatile ExecutorService workExecutor;

    private volatile int state = 0;

    private volatile boolean isSteal = false;

    private ChannelInitHandle initHandle;


    private volatile GunNettyCoreProperty coreProperty;

    GunBootServerImpl() {
        observe = new GunNettyDefaultObserve();
    }

    @Override
    public final boolean isSync() {
        return GunNettyWorkState.getIsSync(state);
    }


    @Override
    public boolean isRunnable() {
        return GunNettyWorkState.getIsRunning(this.state);
    }


    @Override
    public void onHasChannel(ChannelInitHandle initHandle) {
        this.initHandle = initHandle;
    }

    @Override
    public GunBootServer useStealMode(boolean use) {
        this.isSteal = use;
        return this;
    }

    @Override
    public void whenServerChannelStateChanged(SystemChannelChangedHandle handle) {
        this.changedHandle = handle;
    }


    @Override
    public boolean initCheck() {
        if (acceptExecutor == null) {
            throw new GunException(GunExceptionType.NULLPTR, "acceptExecutor is null");
        } else if (workExecutor == null) {
            throw new GunException(GunExceptionType.NULLPTR, "workExecutor is null");
        } else if (isRunnable()) {
            throw new GunException(GunExceptionType.STATE_ERROR, "system has running");
        }
        return true;
    }

    @Override
    public GunTimeEventManager timeManager() {
        return timeManager;
    }

    @Override
    public int stop() throws InterruptedException {
        this.timeManager.stop();
        if (threadManager.stopAndWait()) {
            this.state = 0;
        }
        acceptExecutor.shutdown();
        workExecutor.shutdown();
        return state;
    }

    @Override
    public GunBootServer registerObserve(GunNettyServicesObserve observe) {
        if (observe != null) {
            this.observe = observe;
        }
        return this;
    }

    @Override
    public void setSyncType(boolean b) {
        state = b ? GunNettyWorkState.SYNC.state : GunNettyWorkState.ASYNC.state;
    }

    private void init() {
        coreProperty = GunNettySystemService.coreProperty();
        threadManager = GunNettyCoreThreadManager.
                initInstance(GunNettySystemService.coreProperty());
    }


    @Override
    public synchronized int sync() throws GunNettyCanNotBootException {
        if (baseParameterCheck() == 0) {
            init();
        } else {
            return state | GunNettyWorkState.BOOT_ERROR_2.state;
        }
        try {
            threadManager.init(acceptExecutor, workExecutor, changedHandle, initHandle, coreProperty.getPort());
        } catch (IOException exc) {
            observe.bootFail(exc);
            return state = GunNettyWorkState.BOOT_ERROR_1.state;
        }
        if (this.observe.onBooting(coreProperty)) {
            timeManager.boot(coreProperty.minInterval(), coreProperty.initWait());
            Future<Integer> executing = threadManager.startAndWait();
            this.observe.onBooted(coreProperty);
            state = state | GunNettyWorkState.RUNNING.state;
            if (isSync()) {
                try {
                    int val = executing.get();
                    this.observe.onStatusChanged(GunNettyServicesObserve.GunNettyChangeStatus.RUN_TO_STOP);
                    this.observe.onStop(coreProperty);
                    threadManager.stopAndWait();
                    return val;
                } catch (InterruptedException | ExecutionException e) {
                    observe.runningError(e);
                }
            }
            return state;
        } else {
            return state = GunNettyWorkState.BOOT_ERROR_1.state;
        }
    }

    private int baseParameterCheck() {
        final GunNettyPropertyManager propertyManager = GunNettySystemService.PROPERTY_MANAGER;
        if (!this.initCheck() || !propertyManager.initProperty()) {
            throw new GunException(GunExceptionType.EXC0, "Exception has been threw");
        }
        return 0;
    }


    @Override
    public GunBootServer setExecutors(int var1, int var2) {
        acceptExecutor = GunNettyExecutors.newFixedExecutorPool(10, isSteal);
        workExecutor = GunNettyExecutors.newFixedExecutorPool(10, isSteal);
        return this;
    }
}
