package top.gunplan.netty.impl;


import top.gunplan.netty.GunPipeline;
import top.gunplan.netty.GunTimeExecute;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.nio.channels.SelectionKey;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author dosdrtt
 * @concurrent
 * @apiNote 1.0.0.4
 */
final class CoreThreadManage {
    private static final int MANAGE_THREAD_NUM = Objects.requireNonNull(GunNettyPropertyManagerImpl.coreProperty()).getMaxRunnningNum();
    private volatile static AbstractGunCoreEventLoop dealaccept = null;
    private volatile static AbstractGunCoreEventLoop[] dealdata;
    private static final ScheduledExecutorService TIMER_POOL = Executors.newScheduledThreadPool(1);

    static {
        dealdata = new AbstractGunCoreEventLoop[MANAGE_THREAD_NUM];
    }

    private static final ExecutorService SERVER_POOL = Executors.newFixedThreadPool(MANAGE_THREAD_NUM ^ 1);
    private volatile static GunTimeExecute timeExecute = null;

    private static int slelctSelctor = 0;

    static boolean init(ExecutorService acceptExector, ExecutorService dataExectuor, GunPipeline pilepine, int port) {
        AbstractGunBaseLogUtil.debug("Server running on " + port);
        try {
            dealaccept = new GunCoreConnectionEventLoop(acceptExector, pilepine, port);
            for (int i = 0; i < MANAGE_THREAD_NUM; i++) {
                dealdata[i] = new GunCoreDataEventLoop(dataExectuor, pilepine);
            }
            timeExecute = new GunTimeExecuteImpl();
            timeExecute.registerWorker(pilepine.getTimer());
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
        TIMER_POOL.scheduleAtFixedRate(timeExecute, 100, 1000, TimeUnit.MILLISECONDS);
        return SERVER_POOL.submit(dealaccept, 1);
    }

    static boolean stopAllandWait() {
        SERVER_POOL.shutdown();
        return SERVER_POOL.isTerminated();
    }

    static Set<SelectionKey> getAllofAvaliableClannel(long i) {
        return dealdata[(int) (i & (MANAGE_THREAD_NUM - 1))].getAvaliableSelectionKey();

    }
}
