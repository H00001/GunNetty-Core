
/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;


import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.SystemChannelChangedHandle;
import top.gunplan.netty.impl.property.GunNettyCoreProperty;
import top.gunplan.utils.GunNumberUtil;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * @author dosdrtt
 * @version 2.0.1.9
 */
final class GunNettyCoreThreadManageImpl implements GunNettyCoreThreadManager {

    private final int MANAGE_THREAD_NUM;

    private final GunNettyEventLoopManager eventLoopManager = GunNettyEventLoopManager.newInstance();

    private volatile GunNettyCoreThreadManagerHelper threadHelper;

    private volatile ManagerState status = ManagerState.INACTIVE;


    GunNettyCoreThreadManageImpl(final GunNettyCoreProperty property) {
        MANAGE_THREAD_NUM = GunNumberUtil.isPowOf2(property.maxRunningNum())
                ? property.maxRunningNum() : Runtime.getRuntime().availableProcessors() << 1;
    }

    @Override
    public synchronized boolean init(ExecutorService acceptExecutor, ExecutorService dataExecutor,
                                     SystemChannelChangedHandle pHandle,
                                     ChannelInitHandle cHandle, int port) throws IOException {
        threadHelper = GunNettyCoreThreadManagerHelper.newInstance(MANAGE_THREAD_NUM);
        return eventLoopManager.init(MANAGE_THREAD_NUM, acceptExecutor,
                dataExecutor, pHandle, cHandle, port);
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
        var future = threadHelper.submitConnection(eventLoopManager.connEventLoop());
        threadHelper.waitNext();
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
