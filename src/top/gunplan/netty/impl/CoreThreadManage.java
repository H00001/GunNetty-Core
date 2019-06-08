package top.gunplan.netty.impl;


import top.gunplan.netty.GunPipeline;
import top.gunplan.netty.GunTimeExecute;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.nio.channels.SelectionKey;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author dosdrtt
 * @concurrent
 * @apiNote 1.0.0.5
 */
final class CoreThreadManage {
    private static final GunNettyCoreProperty CORE_PROPERTY = GunNettyPropertyManagerImpl.coreProperty();
    private static final int MANAGE_THREAD_NUM = CORE_PROPERTY.getMaxRunnningNum();
    private volatile static AbstractGunCoreEventLoop dealaccept = null;
    private volatile static AbstractGunCoreEventLoop[] dealdata;
    private static final ScheduledExecutorService TIMER_POOL = Executors.newScheduledThreadPool(1);

    static {
        dealdata = new AbstractGunCoreEventLoop[MANAGE_THREAD_NUM];
    }

    private static final ExecutorService SERVER_POOL = new ThreadPoolExecutor(MANAGE_THREAD_NUM, MANAGE_THREAD_NUM,
            0L, TimeUnit.MILLISECONDS,
            new SynchronousQueue<>(), new GunNettyThreadFactory("CoreDataThread"));

    private static final ExecutorService ACCEPT_POOL = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new SynchronousQueue<>(), new GunNettyThreadFactory("CoreAcceptThread"));
    private volatile static GunTimeExecute timeExecute = null;

    private static int selectSelctor = 0;

    static boolean init(ExecutorService acceptExecutor, ExecutorService dataExecutor, GunPipeline pipepine, int port) {
        AbstractGunBaseLogUtil.debug("Server running on " + port);
        try {
            dealaccept = new GunCoreConnectionEventLoop(acceptExecutor, pipepine, port);
            for (int i = 0; i < MANAGE_THREAD_NUM; i++) {
                dealdata[i] = new GunCoreDataEventLoop(dataExecutor, pipepine);
            }
            timeExecute = new GunTimeExecuteImpl();
            timeExecute.registerWorker(pipepine.getTimer());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static AbstractGunCoreEventLoop getDealThread() {
        return dealdata[selectSelctor++ & (MANAGE_THREAD_NUM - 1)];
    }

    static Future<Integer> startAllAndWait() {
        for (AbstractGunCoreEventLoop dat : dealdata) {
            SERVER_POOL.submit(dat);
        }
        TIMER_POOL.scheduleAtFixedRate(timeExecute, CORE_PROPERTY.initWait(), CORE_PROPERTY.minInterval(), TimeUnit.MILLISECONDS);
        return ACCEPT_POOL.submit(dealaccept, 1);
    }

    static boolean stopAllAndWait() {
        SERVER_POOL.shutdown();
        ACCEPT_POOL.shutdown();
        return (SERVER_POOL.isTerminated() && ACCEPT_POOL.isTerminated());

    }

    static Set<SelectionKey> getAllofAvaliableClannel(long i) {
        return dealdata[(int) (i & (MANAGE_THREAD_NUM - 1))].getAvaliableSelectionKey();

    }
}
