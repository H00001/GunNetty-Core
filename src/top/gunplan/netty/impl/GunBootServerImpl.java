package top.gunplan.netty.impl;

import top.gunplan.netty.*;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * GunBootServer's real implement ,this class is not public
 *
 * @author Gunplan
 * @version 0.0.1.4
 * @apiNote 0.0.0.5
 * @see GunBootServer
 * @since 0.0.0.5
 */

final class GunBootServerImpl implements GunBootServer {


    private volatile boolean runnable = false;

    private volatile GunNettyObserve observe = new GunNettyDefaultObserveImpl();

    private volatile ExecutorService acceptExecutor;

    private volatile ExecutorService requestExecutor;

    private volatile GunNettyPipeline pipeline = new GunNettyPipelineImpl();

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
    public GunNettyPipeline getPipeline() {
        return pipeline;
    }

    @Override
    public void setPipeline(GunNettyPipeline pipeline) {
        if (pipeline != null) {
            this.pipeline = pipeline;
        } else {
            throw new GunException(GunExceptionType.NULLPTR, "Your GunNettyPipeline is null");
        }
    }

    @Override
    public boolean initCheck() {
        return this.acceptExecutor != null && requestExecutor != null && this.pipeline.check().getResult() != GunPipelineCheckResult.CheckResult.ERROR && !runnable;
    }

    @Override
    public int stop() throws InterruptedException {
        if (GunNettyCoreThreadManage.stopAllAndWait()) {
            this.runnable = false;
        }
        return GunNettyWorkState.STOP.state;
    }


    @Override
    public synchronized int sync() throws GunNettyCanNotBootException {
        if (!this.initCheck() || !GunNettyPropertyManagerImpl.initProperty()) {
            throw new GunException(GunExceptionType.EXC0, "Handel, Execute pool not set or Server has been running");
        }
        final GunNettyCoreProperty coreProperty = GunNettyPropertyManagerImpl.coreProperty();
        if (this.observe.onBooting(coreProperty) && GunNettyCoreThreadManage.init(acceptExecutor, requestExecutor, pipeline, coreProperty.getPort())) {
            pipeline.init();
            Future<Integer> result = GunNettyCoreThreadManage.startAllAndWait();
            this.observe.onBooted(coreProperty);
            this.runnable = true;
            if (isSync()) {
                try {
                    int val = result.get();
                    this.observe.onStatusChanged(GunNettyObserve.GunNettyStatus.RUNTOSTOP);
                    this.observe.onStop(coreProperty);
                    return val;
                } catch (InterruptedException | ExecutionException e) {
                    throw new GunNettyCanNotBootException(e);
                }

            } else {
                return (GunNettyWorkState.ASYNC.state | GunNettyWorkState.RUNNING.state);
            }
        }
        return GunNettyWorkState.BOOT_ERROR_1.state;
    }


    @Override
    public GunBootServer setExecuters(ExecutorService acceptExecuters, ExecutorService requestExecuters) {
        this.acceptExecutor = acceptExecuters;
        this.requestExecutor = requestExecuters;
        return this;
    }

}
