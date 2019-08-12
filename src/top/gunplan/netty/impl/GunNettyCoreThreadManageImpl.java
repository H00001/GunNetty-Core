
/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;


import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.GunNettyBaseObserve;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.property.GunNettyCoreProperty;
import top.gunplan.netty.impl.sequence.GunNettySequencer;
import top.gunplan.utils.GunNumberUtil;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * @author dosdrtt
 * @concurrent
 * @apiNote 2.0.1.9
 */
final class GunNettyCoreThreadManageImpl implements GunNettyCoreThreadManager {
    private final GunNettyCoreProperty GUN_NETTY_CORE_PROPERTY;

    private final int MANAGE_THREAD_NUM;

    private final GunNettyBaseObserve observe;

    private final GunNettySequencer sequencer = GunNettySequencer.newThreadSafeSequencer();

    private final GunNettyEventLoopManager eventLoopManager = GunNettyEventLoopManager.newInstance();

    private volatile GunNettyCoreThreadManagerHelper threadHelper;

    private volatile ManagerState status = ManagerState.INACTIVE;


    GunNettyCoreThreadManageImpl(final GunNettyCoreProperty property, final GunNettyBaseObserve baseObserve) {
        this.observe = baseObserve;
        GUN_NETTY_CORE_PROPERTY = property;
        MANAGE_THREAD_NUM = GunNumberUtil.isPowOf2(property.maxRunningNum()) ? property.maxRunningNum() : Runtime.getRuntime().availableProcessors() << 1;
    }

    @Override
    public synchronized boolean init(ExecutorService acceptExecutor, ExecutorService dataExecutor, ChannelInitHandle handle, ChannelInitHandle f, GunNettyPipeline pipeline, int port) {
        threadHelper = GunNettyCoreThreadManagerHelper.newInstance(MANAGE_THREAD_NUM);
        return eventLoopManager.init(MANAGE_THREAD_NUM, pipeline.timer(), acceptExecutor, dataExecutor, handle, f, port);
    }


    @Override
    public ManagerState runState() {
        return status;
    }


    @Override
    public GunDataEventLoop<SocketChannel> dealChannelEventLoop() {
        return eventLoopManager.dealChannelEventLoop(sequencer.nextSequenceInt32WithLimit(MANAGE_THREAD_NUM - 1));
    }

    @Override
    public Future<Integer> startAndWait() {
        status = ManagerState.BOOTING;
        observe.onListen(eventLoopManager.connEventLoop().listenPort());
        threadHelper.submitData(eventLoopManager.dataEventLoop());
        threadHelper.submitTransfer(eventLoopManager.transferEventLoop());
        threadHelper.submitSchedule(eventLoopManager.timeEventLoop(),
                GUN_NETTY_CORE_PROPERTY.initWait(),
                GUN_NETTY_CORE_PROPERTY.minInterval());
        var future = threadHelper.submitConnection(eventLoopManager.connEventLoop());
        status = ManagerState.RUNNING;
        return future;
    }


    @Override
    public boolean stopAndWait() {
        status = ManagerState.STOPPING;
        this.informToStop();
        status = ManagerState.INACTIVE;
        return true;
    }


    private void informToStop() {
        eventLoopManager.shutDown();
        threadHelper.shutdownReturn().syncStop();
    }


}
