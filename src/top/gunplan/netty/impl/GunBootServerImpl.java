package top.gunplan.netty.impl;

import top.gunplan.netty.*;

import top.gunplan.netty.common.GunNettyPropertyManagerImpl;
import top.gunplan.netty.impl.propertys.GunCoreProperty;
import top.gunplan.netty.impl.propertys.GunLogProperty;
import top.gunplan.utils.AbstractGunBaseLogUtil;
import top.gunplan.utils.GunBytesUtil;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * GunBootServer's real implement ,this class is not public
 *
 * @author Gunplan
 * @version 0.0.1.1
 * @apiNote 0.0.0.5
 * @since 0.0.0.4
 */

final class GunBootServerImpl implements GunBootServer {

    private volatile boolean runnable = false;

    private volatile ExecutorService acceptExector;

    private volatile ExecutorService requestExector;

    private final GunPilelineInterface pileline = new GunPilelineImpl();

    GunBootServerImpl() {
    }


    @Override
    public boolean isRunnable() {
        return this.runnable;

    }


    @Override
    public GunPilelineInterface getPipeline() {
        return pileline;
    }

    @Override
    public boolean initCheck() {
        return this.acceptExector != null && requestExector != null && this.pileline.check().getResult() != GunPilelineCheckResult.CheckResult.ERROR && !runnable;
    }


    @Override
    public synchronized void sync() throws ExecutionException, InterruptedException {
        if (!this.initCheck() || !GunNettyPropertyManagerImpl.initProperty()) {
            throw new GunException("Handel, execute pool not set or has been running");
        }
        AbstractGunBaseLogUtil.setLevel(((GunLogProperty) GunNettyPropertyManagerImpl.getProperty("log")).getOutputlevel());
        AbstractGunBaseLogUtil.debug("A high performance net server and a reverse proxy server");
        final GunCoreProperty coreproperty = GunNettyPropertyManagerImpl.getProperty("core");
        AbstractGunBaseLogUtil.outputFile(coreproperty.getProfileName());
        GunBytesUtil.init(coreproperty.getFileReadBufferMin());
        AbstractGunBaseLogUtil.debug("Check parameters succeed");
        if (CoreThreadManage.init(acceptExector, requestExector, pileline, coreproperty.getPort())) {
            Future<Integer> result = CoreThreadManage.startAllAndWait();
            result.get();
        }
    }

    public GunPilelineInterface getPileline() {
        return pileline;
    }

    @Override
    public GunBootServer setExecuters(ExecutorService acceptExecuters, ExecutorService requestExecuters) {
        this.acceptExector = acceptExecuters;
        this.requestExector = requestExecuters;
        return this;
    }

}
