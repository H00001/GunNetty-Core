
/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;


import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.SystemChannelChangedHandle;
import top.gunplan.netty.impl.property.GunNettyCoreProperty;
import top.gunplan.utils.GunNumberUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * @author dosdrtt
 * @apiNote 2.0.1.9
 */
final class GunNettyCoreThreadManageImpl implements GunNettyCoreThreadManager {
    /**
     * GunNettyCoreProperty
     */
    private final GunNettyCoreProperty GUN_NETTY_CORE_PROPERTY;

    private final int MANAGE_THREAD_NUM;

    private final GunNettyEventLoopManager eventLoopManager = GunNettyEventLoopManager.newInstance();

    private volatile GunNettyCoreThreadManagerHelper threadHelper;

    private volatile ManagerState status = ManagerState.INACTIVE;


    GunNettyCoreThreadManageImpl(final GunNettyCoreProperty property) {
        GUN_NETTY_CORE_PROPERTY = property;
        MANAGE_THREAD_NUM = GunNumberUtil.isPowOf2(property.maxRunningNum()) ? property.maxRunningNum() : Runtime.getRuntime().availableProcessors() << 1;
    }

    @Override
    public synchronized boolean init(ExecutorService acceptExecutor, ExecutorService dataExecutor, SystemChannelChangedHandle pHandle, ChannelInitHandle cHandle, int port) {
        threadHelper = GunNettyCoreThreadManagerHelper.newInstance(MANAGE_THREAD_NUM);
        return eventLoopManager.init(MANAGE_THREAD_NUM, acceptExecutor, dataExecutor, pHandle, cHandle, port);
    }


    @Override
    public ManagerState runState() {
        return status;
    }


    @Override
    public Future<Integer> startAndWait() {
        status = ManagerState.BOOTING;
        threadHelper.submitData(eventLoopManager.dataEventLoop());
        threadHelper.submitTransfer(eventLoopManager.transferEventLoop());
        assert eventLoopManager.connEventLoop() != null;
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
