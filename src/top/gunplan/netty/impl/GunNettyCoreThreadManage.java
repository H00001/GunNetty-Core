package top.gunplan.netty.impl;


import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.GunNettySystemServices;
import top.gunplan.netty.GunTimeExecute;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;

import top.gunplan.utils.GunLogger;

import java.io.IOException;
import java.nio.channels.SelectionKey;


import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author dosdrtt
 * @concurrent
 * @apiNote 1.0.0.8
 */
final class GunNettyCoreThreadManage {
    private static final GunNettyCoreProperty CORE_PROPERTY = GunNettySystemServices.coreProperty();
    private static final int MANAGE_THREAD_NUM = CORE_PROPERTY.getMaxRunnningNum();
    private volatile static AbstractGunCoreEventLoop dealAccept = null;
    private volatile static AbstractGunCoreEventLoop[] dealData;
    static volatile boolean status = true;
    private final static GunLogger LOG = GunNettyContext.logger;
    private volatile static GunNettyTransfer<SocketChannel> transfer;
    private static final ScheduledExecutorService TIMER_POOL = Executors.newScheduledThreadPool(1);

    static {
        dealData = new AbstractGunCoreEventLoop[MANAGE_THREAD_NUM];
    }

    private static final ExecutorService SERVER_POOL = GunNettyExecutors.newFixedExecutorPool(MANAGE_THREAD_NUM, "CoreDataThread");
    private static final ExecutorService TRANSFER_POOL = GunNettyExecutors.newSignalExecutorPool("TransferThread");
    private static final ExecutorService ACCEPT_POOL = GunNettyExecutors.newSignalExecutorPool("CoreAcceptThread");
    private static int selectSelector = 0;
    private volatile static GunTimeExecute timeExecute = null;


    static boolean init(ExecutorService acceptExecutor, ExecutorService dataExecutor, GunNettyPipeline pipepine, int port) {
        LOG.info("Server running on :" + port);
        transfer = new GunNettyBaseTransferEventLoop<>();
        timeExecute = new GunNettyTimeExecuteImpl();
        try {
            dealAccept = new GunCoreConnectionEventLoop(acceptExecutor, pipepine, port);
            for (int i = 0; i < dealData.length; i++) {
                dealData[i] = new GunCoreDataEventLoop(dataExecutor, pipepine);
            }
            timeExecute.registerWorker(pipepine.getTimer());
        } catch (IOException e) {
            LOG.error(e);
            return false;
        }
        return true;
    }

    static <U extends SocketChannel> void push(U u) {
        transfer.push(u);
    }

    static AbstractGunCoreEventLoop getDealThread() {
        return dealData[selectSelector++ & (MANAGE_THREAD_NUM - 1)];
    }

    static Future<Integer> startAllAndWait() {
        for (AbstractGunCoreEventLoop dat : dealData) {
            SERVER_POOL.submit(dat);
        }
        TRANSFER_POOL.submit(transfer);
        TIMER_POOL.scheduleAtFixedRate(timeExecute, CORE_PROPERTY.initWait(), CORE_PROPERTY.minInterval(), TimeUnit.MILLISECONDS);
        return ACCEPT_POOL.submit(dealAccept, 1);
    }

    static boolean stopAllAndWait() throws InterruptedException {
        status = false;
        SERVER_POOL.shutdown();
        ACCEPT_POOL.shutdown();
        TIMER_POOL.shutdown();
        for (; SERVER_POOL.isTerminated() && ACCEPT_POOL.isTerminated() && TIMER_POOL.isTerminated(); ) {
            if (!SERVER_POOL.isTerminated()) {
                SERVER_POOL.awaitTermination(1, TimeUnit.MINUTES);
            } else if (!ACCEPT_POOL.isTerminated()) {
                ACCEPT_POOL.awaitTermination(1, TimeUnit.MINUTES);
            } else if (!TIMER_POOL.isTerminated()) {
                TIMER_POOL.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
        return true;

    }

    static Set<SelectionKey> getAvailableChannel(long i) {
        return dealData[(int) (i & (MANAGE_THREAD_NUM - 1))].getAvailableSelectionKey();
    }
}
