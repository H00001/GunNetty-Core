/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.SystemChannelChangedHandle;
import top.gunplan.netty.impl.eventloop.*;
import top.gunplan.netty.impl.sequence.GunNettySequencer;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

/**
 * GunNettyEventLoopManagerImpl
 *
 * @author frank albert
 * @version 0.0.0.2
 */
final class GunNettyEventLoopManagerImpl implements GunNettyEventLoopManager {
    private volatile GunConnEventLoop dealAccept;
    private volatile GunDataEventLoop<SocketChannel>[] dealData;
    private volatile GunNettyTransferEventLoop<SocketChannel> transfer;
    private volatile int dataEvenLoopSum;
    private GunNettySequencer sequencer0 = GunNettySequencer.newThreadUnSafeSequencer();

    @Override
    public synchronized boolean init(int v1, ExecutorService bossExecutor,
                                     ExecutorService dataExecutor, SystemChannelChangedHandle parentHandle,
                                     ChannelInitHandle childrenHandle, int port) throws IOException {
        this.dataEvenLoopSum = v1;
        transfer = GunNettyEventLoopFactory.newGunNettyBaseTransfer();
        transfer.registerManager(this);
        dealData = GunNettyEventLoopFactory.buildDataEventLoop(v1).with(dataExecutor).andRegister(this).build();
        dealAccept = GunNettyEventLoopFactory.buildConnEventLoop().with(bossExecutor, parentHandle, childrenHandle)
                .bindPort(port).andRegister(this).build();
        return true;
    }

    @Override
    public GunDataEventLoop<SocketChannel> dealChannelEventLoop() {
        return dealData[sequencer0.nextSequenceInt32WithLimit(dataEvenLoopSum)];
    }


    @Override
    public GunNettyTransferEventLoop<SocketChannel> transferEventLoop() {
        return transfer;
    }

    @Override
    public GunConnEventLoop connEventLoop() {
        return dealAccept;
    }

    @Override
    public GunDataEventLoop[] dataEventLoop() {
        return dealData;
    }


    @Override
    public void shutDown() {
        this.dealAccept.stopEventLoop();
        this.transfer.stopEventLoop();
        Arrays.stream(dealData).parallel().forEach(GunNettyVariableWorker::stopEventLoop);
    }
}
