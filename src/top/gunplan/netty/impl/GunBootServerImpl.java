package top.gunplan.netty.impl;

import top.gunplan.netty.*;

import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * GunBootServer's real implement ,this class is not public
 *
 * @see GunBootServer
 * @author Gunplan
 * @version 0.0.1.3
 * @apiNote 0.0.0.5
 * @since 0.0.0.4
 */

final class GunBootServerImpl implements GunBootServer {


    private volatile boolean runnable = false;

    private volatile GunNettyObserve observe = new GunNettyDefaultObserveImpl();

    private volatile ExecutorService acceptExector;

    private volatile ExecutorService requestExector;

    private volatile GunPipeline pileline = new GunPipelineImpl();

    GunBootServerImpl() {
    }


    @Override
    public GunBootServer registerObserve(GunNettyObserve observe) {
        if (observe != null) {
            this.observe = observe;
        }
        return this;
    }

    @Override
    public boolean isRunnable() {
        return this.runnable;

    }


    @Override
    public GunPipeline getPipeline() {
        return pileline;
    }

    public void setPileline(GunPipeline pileline) {
        if (pileline != null) {
            this.pileline = pileline;
        } else {
            throw new GunException("Your GunPipeline is null");
        }
    }

    @Override
    public boolean initCheck() {
        return this.acceptExector != null && requestExector != null && this.pileline.check().getResult() != GunPipelineCheckResult.CheckResult.ERROR && !runnable;
    }

    @Override
    public void stop() {
        if (CoreThreadManage.stopAllandWait()) {
            this.runnable = false;
        }
    }


    @Override
    public synchronized int sync() throws ExecutionException, InterruptedException {
        if (!this.initCheck() || !GunNettyPropertyManagerImpl.initProperty()) {
            throw new GunException("Handel, Execute pool not set or Server has been running");
        }
        final GunNettyCoreProperty coreProperty = GunNettyPropertyManagerImpl.coreProperty();
        if (this.observe.onBooting(coreProperty) && CoreThreadManage.init(acceptExector, requestExector, pileline, coreProperty.getPort())) {
            Future<Integer> result = CoreThreadManage.startAllAndWait();
            this.observe.onBooted(coreProperty);
            this.runnable = true;
            int val = result.get();
            this.observe.onStatusChanged(GunNettyObserve.GunNettyStatus.RUNTOSTOP);
            this.observe.onStop(coreProperty);
            return val;
        }
        return -1;
    }


    @Override
    public GunBootServer setExecuters(ExecutorService acceptExecuters, ExecutorService requestExecuters) {
        this.acceptExector = acceptExecuters;
        this.requestExector = requestExecuters;
        return this;
    }

}
