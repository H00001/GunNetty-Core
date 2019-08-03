package top.gunplan.netty.impl;


import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.GunNettySystemServices;
import top.gunplan.netty.GunTimeExecutor;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.impl.eventloop.EventLoopFactory;
import top.gunplan.netty.impl.eventloop.GunConnEventLoop;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
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
 * @apiNote 2.0.0.8
 */
final class GunNettyCoreThreadManageImpl implements GunNettyCoreThreadManager {
    private final static GunLogger LOG = GunNettyContext.logger.setTAG(GunNettyCoreThreadManageImpl.class);
    private final static GunNettyCoreProperty GUN_NETTY_CORE_PROPERTY = GunNettySystemServices.coreProperty();
    private final int MANAGE_THREAD_NUM = GUN_NETTY_CORE_PROPERTY.getMaxRunnningNum();
    private volatile GunConnEventLoop dealAccept;
    private final GunTimeExecutor timeExecute = new GunNettyTimeExecuteImpl();
    private volatile GunDataEventLoop<SocketChannel>[] dealData;
    private final GunNettyTransfer<SocketChannel> transfer;

    private final ScheduledExecutorService TIMER____POOL;
    private final ExecutorService SERVER___POOL;
    private final ExecutorService TRANSFER_POOL;
    private final ExecutorService ACCEPT___POOL;

    private static int selectSelector = 0;
    private volatile int port;
    private volatile ManagerState status = ManagerState.INACTIVE;


    GunNettyCoreThreadManageImpl() {
        TIMER____POOL = Executors.newScheduledThreadPool(1);
        TRANSFER_POOL = GunNettyExecutors.newSignalExecutorPool("TransferThread");
        ACCEPT___POOL = GunNettyExecutors.newSignalExecutorPool("CoreAcceptThread");
        SERVER___POOL = GunNettyExecutors.newFixedExecutorPool(MANAGE_THREAD_NUM, "CoreDataThread");
        transfer = EventLoopFactory.newGunDisruptorTransfer();
        transfer.registerManager(this);

    }

    @Override
    public synchronized boolean init(ExecutorService acceptExecutor, ExecutorService dataExecutor, GunNettyPipeline pipeline, int port) throws IOException {
        this.port = port;
        timeExecute.registerWorker(pipeline.timer());
        timeExecute.registerManager(this);
        dealData = EventLoopFactory.buildDataEventLoop(MANAGE_THREAD_NUM).with(dataExecutor, pipeline).andRegister(this).build();
        dealAccept = EventLoopFactory.buildConnEventLoop().bindPort(port).with(dataExecutor, pipeline).andRegister(this).build();
        return true;
    }


    @Override
    public ManagerState runState() {
        return status;
    }


    @Override
    public GunDataEventLoop<SocketChannel> dealChannelEventLoop() {
        return dealData[selectSelector++ & (MANAGE_THREAD_NUM - 1)];
    }

    @Override
    public Future<Integer> startAndWait() {
        status = ManagerState.BOOTING;
        LOG.info("Server running on :" + port);
        for (GunCoreEventLoop dat : dealData) {
            SERVER___POOL.submit(dat);
        }
        TRANSFER_POOL.submit(transfer);
        TIMER____POOL.scheduleAtFixedRate(timeExecute, GUN_NETTY_CORE_PROPERTY.initWait(), GUN_NETTY_CORE_PROPERTY.minInterval(), TimeUnit.MILLISECONDS);

        var future = ACCEPT___POOL.submit(dealAccept, 1);
        status = ManagerState.RUNNING;
        return future;
    }

    @Override
    public GunNettyTransfer<SocketChannel> transferThread() {
        return transfer;
    }

    @Override
    public boolean stopAndWait() throws InterruptedException {
        status = ManagerState.STOPPING;
        inforToStop();

        for (; SERVER___POOL.isTerminated() &&
                ACCEPT___POOL.isTerminated() &&
                TIMER____POOL.isTerminated() &&
                TRANSFER_POOL.isTerminated(); ) {
            if (!SERVER___POOL.isTerminated()) {
                SERVER___POOL.awaitTermination(1, TimeUnit.MINUTES);
            } else if (!ACCEPT___POOL.isTerminated()) {
                ACCEPT___POOL.awaitTermination(1, TimeUnit.MINUTES);
            } else if (!TIMER____POOL.isTerminated()) {
                TIMER____POOL.awaitTermination(1, TimeUnit.MINUTES);
            } else if (!TRANSFER_POOL.isTerminated()) {
                TIMER____POOL.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
        status = ManagerState.INACTIVE;
        return true;
    }

    private void inforToStop() {
        dealAccept.stopEventLoop();
        transfer.stopEventLoop();
        for (GunCoreEventLoop dealDatum : dealData) {
            dealDatum.stopEventLoop();
        }
        ACCEPT___POOL.shutdown();
        TRANSFER_POOL.shutdown();
        SERVER___POOL.shutdown();
        TIMER____POOL.shutdown();
    }

    @Override
    public Set<SelectionKey> availableChannel(long i) {
        return dealData[(int) (i & (MANAGE_THREAD_NUM - 1))].availableSelectionKey();
    }
}
