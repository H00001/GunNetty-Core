package top.gunplan.netty.impl;


import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.GunNettySystemServices;
import top.gunplan.netty.GunTimeExecute;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.impl.eventloop.EventLoopFactory;
import top.gunplan.netty.impl.eventloop.GunNettyTransfer;
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
final class GunNettyCoreThreadManageImpl implements GunNettyCoreThreadManager {
    private final static GunLogger LOG = GunNettyContext.logger;
    private volatile GunNettyCoreProperty CORE_PROPERTY;
    private volatile int MANAGE_THREAD_NUM;
    private volatile GunCoreEventLoop dealAccept;
    private final GunTimeExecute timeExecute = new GunNettyTimeExecuteImpl();
    private volatile GunCoreEventLoop[] dealData;
    private final GunNettyTransfer<SocketChannel> transfer = EventLoopFactory.newGunNettyBaseTransfer().registerManager(this);


    private final ScheduledExecutorService TIMER_POOL = Executors.newScheduledThreadPool(1);
    private volatile ExecutorService SERVER_POOL;
    private final ExecutorService TRANSFER_POOL = GunNettyExecutors.newSignalExecutorPool("TransferThread");
    private final ExecutorService ACCEPT_POOL = GunNettyExecutors.newSignalExecutorPool("CoreAcceptThread");

    private static int selectSelector = 0;
    private volatile int port;
    private volatile ManageState status = ManageState.STOPED;


    GunNettyCoreThreadManageImpl() {

    }

    @Override
    public synchronized boolean init(ExecutorService acceptExecutor, ExecutorService dataExecutor, GunNettyPipeline pipeline, int port) throws IOException {
        this.port = port;
        CORE_PROPERTY = GunNettySystemServices.coreProperty();
        initPoolAndEventLoop();
        timeExecute.registerWorker(pipeline.getTimer());
        dealAccept = new GunCoreConnectionEventLoop(acceptExecutor, pipeline, port).registerManager(this);
        for (int i = 0; i < dealData.length; i++) {
            dealData[i] = new GunCoreDataEventLoop(dataExecutor, pipeline).registerManager(this);
        }
        return true;
    }

    private void initPoolAndEventLoop() {
        MANAGE_THREAD_NUM = CORE_PROPERTY.getMaxRunnningNum();
        SERVER_POOL = GunNettyExecutors.newFixedExecutorPool(MANAGE_THREAD_NUM, "CoreDataThread");
        dealData = new AbstractGunCoreEventLoop[MANAGE_THREAD_NUM];
    }

    @Override
    public ManageState runState() {
        return status;
    }


    @Override
    public GunCoreEventLoop dealChannelThread() {
        return dealData[selectSelector++ & (MANAGE_THREAD_NUM - 1)];
    }

    @Override
    public Future<Integer> startAllAndWait() {
        status = ManageState.BOOTING;
        LOG.info("Server running on :" + port);
        for (GunCoreEventLoop dat : dealData) {
            SERVER_POOL.submit(dat);
        }
        TRANSFER_POOL.submit(transfer);
        TIMER_POOL.scheduleAtFixedRate(timeExecute, CORE_PROPERTY.initWait(), CORE_PROPERTY.minInterval(), TimeUnit.MILLISECONDS);
        var frature = ACCEPT_POOL.submit(dealAccept, 1);
        status = ManageState.RUNNING;
        return frature;
    }

    @Override
    public GunNettyTransfer<SocketChannel> transferThread() {
        return transfer;
    }

    @Override
    public boolean stopAllAndWait() throws InterruptedException {
        status = ManageState.STOPPING;
        dealAccept.stopEventLoop();
        transfer.stopEventLoop();
        for (GunCoreEventLoop dealDatum : dealData) {
            dealDatum.stopEventLoop();
        }
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
        status = ManageState.STOPED;
        return true;

    }

    @Override
    public Set<SelectionKey> availableChannel(long i) {
        return dealData[(int) (i & (MANAGE_THREAD_NUM - 1))].availableSelectionKey();
    }
}
