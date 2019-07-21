package top.gunplan.netty.impl;

import top.gunplan.netty.*;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.netty.property.GunNettyPropertyManager;

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
            throw new GunException(GunExceptionType.NULLPTR, "Your GunNetty Pipeline is null");
        }
    }

    @Override
    public boolean initCheck() {
        return this.acceptExecutor != null && requestExecutor != null && this.pipeline.check().getResult() != GunPipelineCheckResult.CheckResult.ERROR && !runnable;
    }

    @Override
    public int stop() throws InterruptedException {
        this.pipeline.destroy();
        if (GunNettyCoreThreadManage.stopAllAndWait()) {
            this.runnable = false;
        }
        return GunNettyWorkState.STOP.state;
    }


    @Override
    public synchronized int sync() throws GunNettyCanNotBootException {
        final GunNettyPropertyManager propertyManager = GunNettySystemServices.PROPERTY_MANAGER;
        if (!this.initCheck() || !propertyManager.initProperty()) {
            throw new GunException(GunExceptionType.EXC0, "Handel, Execute pool not set or Server has been running");
        }
        final GunNettyCoreProperty coreProperty = GunNettySystemServices.coreProperty();
        if (this.observe.onBooting(coreProperty) && GunNettyCoreThreadManage.init(acceptExecutor, requestExecutor, pipeline, coreProperty.getPort())) {
            pipeline.init();
            Future<Integer> executing = GunNettyCoreThreadManage.startAllAndWait();
            this.observe.onBooted(coreProperty);
            this.runnable = true;
            if (isSync()) {
                try {
                    int val = executing.get();
                    pipeline.destroy();
                    this.observe.onStatusChanged(GunNettyObserve.GunNettyChangeStatus.RUN_TO_STOP);
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
