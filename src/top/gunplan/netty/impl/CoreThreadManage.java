package top.gunplan.netty.impl;


import top.gunplan.netty.GunPipeline;
import top.gunplan.netty.common.GunNettyPropertyManagerImpl;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author dosdrtt
 */
final class CoreThreadManage {
    private static final int MANAGE_THREAD_NUM = ((GunNettyCoreProperty) Objects.requireNonNull(GunNettyPropertyManagerImpl.getProperty("core"))).getMaxRunnningNum();
    private volatile static AbstractGunCoreEventLoop dealaccept = null;
    private volatile static AbstractGunCoreEventLoop[] dealdata;
    private volatile static GunTimerTaskEventLoop dealtime;

    static {
        dealdata = new AbstractGunCoreEventLoop[MANAGE_THREAD_NUM];
    }

    private static final ExecutorService SERVER_POOL = Executors.newFixedThreadPool(MANAGE_THREAD_NUM ^ 1);
    private static final ExecutorService TIMER_POOL = Executors.newScheduledThreadPool(1);

    private static int slelctSelctor = 0;

    static boolean init(ExecutorService acceptExector, ExecutorService dataExectuor, GunPipeline pilepine, int port) {
        AbstractGunBaseLogUtil.debug("Server running on " + port);
        try {
            dealaccept = new GunCoreConnectionEventLoop(acceptExector, pilepine, port);
            for (int i = 0; i < MANAGE_THREAD_NUM; i++) {
                dealdata[i] = new GunCoreDataEventLoop(dataExectuor, pilepine);
            }
            //todo
          //  dealtime = new GunTimerTaskEventLoop(pilepine.getTimer());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static AbstractGunCoreEventLoop getDealThread() {
        return dealdata[slelctSelctor++ & (MANAGE_THREAD_NUM - 1)];
    }

    static Future<Integer> startAllAndWait() {
        for (AbstractGunCoreEventLoop dat : dealdata) {
            SERVER_POOL.submit(dat);
        }
        //TIMER_POOL.execute();
        return SERVER_POOL.submit(dealaccept, 1);
    }

    static boolean stopAllandWait() {
        SERVER_POOL.shutdown();
        return SERVER_POOL.isTerminated();
    }
}
