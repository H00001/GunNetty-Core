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
 * @version 0.0.1.4
 * @apiNote 0.0.0.5
 * @since 0.0.0.4
 */

final class GunBootServerImpl implements GunBootServer {


    private volatile boolean runnable = false;

    private volatile GunNettyObserve observe = new GunNettyDefaultObserveImpl();

    private volatile ExecutorService acceptExecutor;

    private volatile ExecutorService requestExecutor;

    private volatile GunPipeline pipeline = new GunPipelineImpl();

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
        return pipeline;
    }

    @Override
    public void setPipeline(GunPipeline pipeline) {
        if (pipeline != null) {
            this.pipeline = pipeline;
        } else {
            throw new GunException(GunExceptionTypes.NULLPTR, "Your GunPipeline is null");
        }
    }

    @Override
    public boolean initCheck() {
        return this.acceptExecutor != null && requestExecutor != null && this.pipeline.check().getResult() != GunPipelineCheckResult.CheckResult.ERROR && !runnable;
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
        if (this.observe.onBooting(coreProperty) && CoreThreadManage.init(acceptExecutor, requestExecutor, pipeline, coreProperty.getPort())) {
            pipeline.init();
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
        this.acceptExecutor = acceptExecuters;
        this.requestExecutor = requestExecuters;
        return this;
    }

}
