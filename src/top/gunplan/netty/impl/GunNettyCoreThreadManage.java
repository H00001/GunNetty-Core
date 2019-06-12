package top.gunplan.netty.impl;


import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.GunTimeExecute;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author dosdrtt
 * @concurrent
 * @apiNote 1.0.0.8
 */
final class GunNettyCoreThreadManage {
    private static final GunNettyCoreProperty CORE_PROPERTY = GunNettyPropertyManagerImpl.coreProperty();
    private static final int MANAGE_THREAD_NUM = CORE_PROPERTY.getMaxRunnningNum();
    private volatile static AbstractGunCoreEventLoop dealaccept = null;
    private volatile static AbstractGunCoreEventLoop[] dealdata;
    private static final BlockingQueue<SocketChannel> KEY_QUEUE = new LinkedBlockingQueue<>();
    static volatile boolean status = true;
    private volatile static GunCoreEventLoop transfer;
    private static final ScheduledExecutorService TIMER_POOL = Executors.newScheduledThreadPool(1);

    static {
        dealdata = new AbstractGunCoreEventLoop[MANAGE_THREAD_NUM];
    }

    private static SynchronousQueue<Runnable> sync = new SynchronousQueue<>();
    private static final ExecutorService SERVER_POOL = new ThreadPoolExecutor(MANAGE_THREAD_NUM, MANAGE_THREAD_NUM,
            0L, TimeUnit.MILLISECONDS, sync, new GunNettyThreadFactory("CoreDataThread"));
    private static final ExecutorService TRANSFER_POOL = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS, sync, new GunNettyThreadFactory("TransferThread"));
    private static final ExecutorService ACCEPT_POOL = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS, sync, new GunNettyThreadFactory("CoreAcceptThread"));
    private static int selectSelector = 0;
    private volatile static GunTimeExecute timeExecute = null;

    static Queue<SocketChannel> keyQueue() {
        return KEY_QUEUE;
    }

    static boolean init(ExecutorService acceptExecutor, ExecutorService dataExecutor, GunNettyPipeline pipepine, int port) {
        AbstractGunBaseLogUtil.debug("Server running on :" + port);
        transfer = new GunNettyTransferEventLoop(KEY_QUEUE);
        timeExecute = new GunNettyTimeExecuteImpl();
        try {
            dealaccept = new GunCoreConnectionEventLoop(acceptExecutor, pipepine, port);
            for (int i = 0; i < dealdata.length; i++) {
                dealdata[i] = new GunCoreDataEventLoop(dataExecutor, pipepine);
            }
            timeExecute.registerWorker(pipepine.getTimer());
        } catch (Exception e) {
            AbstractGunBaseLogUtil.error(e);
            return false;
        }
        return true;
    }

    static AbstractGunCoreEventLoop getDealThread() {
        return dealdata[selectSelector++ & (MANAGE_THREAD_NUM - 1)];
    }

    static Future<Integer> startAllAndWait() {
        for (AbstractGunCoreEventLoop dat : dealdata) {
            SERVER_POOL.submit(dat);
        }
        TRANSFER_POOL.submit(transfer);
        TIMER_POOL.scheduleAtFixedRate(timeExecute, CORE_PROPERTY.initWait(), CORE_PROPERTY.minInterval(), TimeUnit.MILLISECONDS);
        return ACCEPT_POOL.submit(dealaccept, 1);
    }

    static boolean stopAllAndWait() {
        status = false;
        SERVER_POOL.shutdown();
        ACCEPT_POOL.shutdown();
        TIMER_POOL.shutdown();
        for (; SERVER_POOL.isTerminated() && ACCEPT_POOL.isTerminated() && TIMER_POOL.isTerminated(); ) {
        }
        return true;

    }

    static Set<SelectionKey> getAvailableClannel(long i) {
        return dealdata[(int) (i & (MANAGE_THREAD_NUM - 1))].getAvailableSelectionKey();
    }
}
