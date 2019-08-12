
/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;


import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.GunNettyBaseObserve;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.impl.eventloop.*;
import top.gunplan.netty.impl.property.GunNettyCoreProperty;
import top.gunplan.netty.impl.timeevent.AbstractGunTimeExecutor;
import top.gunplan.netty.impl.timeevent.GunTimeExecutor;
import top.gunplan.utils.NumberUtil;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * @author dosdrtt
 * @concurrent
 * @apiNote 2.0.1.9
 */
final class GunNettyCoreThreadManageImpl implements GunNettyCoreThreadManager {
    private final GunNettyCoreProperty GUN_NETTY_CORE_PROPERTY;
    private final int MANAGE_THREAD_NUM;
    private volatile GunConnEventLoop dealAccept;
    private final GunNettyBaseObserve observe;
    private final GunNettySequencer sequencer = new GunUnsafeNettySequenceImpl();
    private final GunTimeExecutor timeExecute = AbstractGunTimeExecutor.create();
    private volatile GunDataEventLoop<SocketChannel>[] dealData;
    private final GunNettyTransfer<SocketChannel> transfer;

    private final ScheduledExecutorService EXETIMER_POOL;
    private final ExecutorService DASERVER_POOL;
    private final ExecutorService TRANSFER_POOL;
    private final ExecutorService ACCEPTCO_POOL;
    private final ExecutorService[] EXE_POOL_LIST;


    private volatile ManagerState status = ManagerState.INACTIVE;


    GunNettyCoreThreadManageImpl(final GunNettyCoreProperty property, final GunNettyBaseObserve baseObserve) {
        this.observe = baseObserve;
        GUN_NETTY_CORE_PROPERTY = property;
        MANAGE_THREAD_NUM = NumberUtil.isPowOf2(property.maxRunningNum()) ? property.maxRunningNum() : Runtime.getRuntime().availableProcessors() << 1;
        EXETIMER_POOL = Executors.newScheduledThreadPool(1);
        TRANSFER_POOL = GunNettyExecutors.newSignalExecutorPool("CoreTransferThread");
        ACCEPTCO_POOL = GunNettyExecutors.newSignalExecutorPool("CoreAcceptThread");
        DASERVER_POOL = GunNettyExecutors.newFixedExecutorPool(MANAGE_THREAD_NUM, "CoreDataThread");
        EXE_POOL_LIST = new ExecutorService[]{ACCEPTCO_POOL, TRANSFER_POOL, DASERVER_POOL, EXETIMER_POOL};

        transfer = EventLoopFactory.newGunDisruptorTransfer();
        transfer.registerManager(this);

    }

    @Override
    public synchronized boolean init(ExecutorService acceptExecutor, ExecutorService dataExecutor, ChannelInitHandle handle, GunNettyPipeline pipeline, int port) throws IOException {
        timeExecute.registerWorker(pipeline.timer());
        timeExecute.registerManager(this);
        dealData = EventLoopFactory.buildDataEventLoop(MANAGE_THREAD_NUM).with(dataExecutor).andRegister(this).build();
        dealAccept = EventLoopFactory.buildConnEventLoop().bindPort(port).with(dataExecutor, handle).andRegister(this).build();
        return true;
    }


    @Override
    public ManagerState runState() {
        return status;
    }


    @Override
    public GunDataEventLoop<SocketChannel> dealChannelEventLoop() {
        return dealData[sequencer.nextSequenceInt32WithLimit(MANAGE_THREAD_NUM - 1)];
    }

    @Override
    public Future<Integer> startAndWait() {
        status = ManagerState.BOOTING;
        observe.onListen(dealAccept.listenPort());
        Arrays.stream(dealData).parallel().forEach(DASERVER_POOL::submit);
        TRANSFER_POOL.submit(transfer);
        EXETIMER_POOL.scheduleAtFixedRate(timeExecute, GUN_NETTY_CORE_PROPERTY.initWait(),
                GUN_NETTY_CORE_PROPERTY.minInterval(), TimeUnit.MILLISECONDS);
        var future = ACCEPTCO_POOL.submit(dealAccept, 1);
        status = ManagerState.RUNNING;
        return future;
    }

    @Override
    public GunNettyTransfer<SocketChannel> transferThread() {
        return transfer;
    }

    @Override
    public boolean stopAndWait() {
        status = ManagerState.STOPPING;
        this.informToStop();
        this.checkToStop();
        status = ManagerState.INACTIVE;
        return true;
    }

    private void checkToStop() {
        Stream<ExecutorService> services = Arrays.stream(EXE_POOL_LIST).filter(who -> !who.isTerminated());
        services.parallel().forEach(any -> {
            try {
                any.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException ignore) {
            }
        });
    }


    private void informToStop() {
        this.dealAccept.stopEventLoop();
        this.transfer.stopEventLoop();
        this.timeExecute.shutdown();
        Arrays.stream(dealData).parallel().forEach(GunNettyVariableWorker::stopEventLoop);
        Arrays.stream(EXE_POOL_LIST).forEach(ExecutorService::shutdown);
    }

    @Override
    public Set<SelectionKey> availableChannel(long i) {
        return dealData[(int) (i & (MANAGE_THREAD_NUM - 1))].availableSelectionKey();
    }
}
