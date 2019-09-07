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
 * @date 2019-08-13 04:42
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
                                     ChannelInitHandle childrenHandle, int port) {
        this.dataEvenLoopSum = v1;
        transfer = EventLoopFactory.newGunNettyBaseTransfer();
        transfer.registerManager(this);
        try {
            dealData = EventLoopFactory.buildDataEventLoop(v1).with(bossExecutor).andRegister(this).build();
            dealAccept = EventLoopFactory.buildConnEventLoop().with(dataExecutor, parentHandle, childrenHandle)
                    .bindPort(port).andRegister(this).build();
        } catch (IOException e) {
            return false;
        }
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
