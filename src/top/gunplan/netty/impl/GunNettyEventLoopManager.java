/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.impl.eventloop.GunConnEventLoop;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.eventloop.GunNettyTransfer;
import top.gunplan.netty.impl.timeevent.GunTimeExecutor;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * GunNettyEventLoopManager
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-13 04:41
 */
public interface GunNettyEventLoopManager {
    static GunNettyEventLoopManager newInstance() {
        return new GunNettyEventLoopManagerImpl();
    }


    boolean init(int v1, List<GunNettyTimer> timerList, ExecutorService bossExecutor,
                 ExecutorService dataExecutor, ChannelInitHandle parentHandle,
                 ChannelInitHandle childrenHandle, int port);


    GunDataEventLoop<SocketChannel> dealChannelEventLoop(int i);


    Set<SelectionKey> availableChannel(int i);


    GunNettyTransfer<SocketChannel> transferEventLoop();


    GunConnEventLoop connEventLoop();


    GunDataEventLoop[] dataEventLoop();

    GunTimeExecutor timeEventLoop();

    void shutDown();
}
