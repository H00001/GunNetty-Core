/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.SystemChannelChangedHandle;
import top.gunplan.netty.impl.eventloop.GunConnEventLoop;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.eventloop.GunNettyTransferEventLoop;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

/**
 * GunNettyEventLoopManager
 *
 * @author frank albert
 * @version 0.0.0.3
 */
public interface GunNettyEventLoopManager {
    /**
     * create a new event loop manage instance
     *
     * @return instance
     */
    static GunNettyEventLoopManager newInstance() {
        return new GunNettyEventLoopManagerImpl();
    }

    /**
     * init
     *
     * @param v1             sum
     * @param bossExecutor   to deal connection event
     * @param dataExecutor   to deal i/o event
     * @param parentHandle   parent handle
     * @param childrenHandle children handle
     * @param port           open port
     * @return init result
     */
    boolean init(int v1, ExecutorService bossExecutor,
                 ExecutorService dataExecutor, SystemChannelChangedHandle parentHandle,
                 ChannelInitHandle childrenHandle, int port);


    /**
     * dealChannelEventLoop
     * is data thread
     * who can deal channel event? only Data EventLoop
     *
     * @return GunDataEventLoop
     */
    GunDataEventLoop<SocketChannel> dealChannelEventLoop();


    /**
     * transfer event loop
     *
     * @return event loop
     */
    GunNettyTransferEventLoop<SocketChannel> transferEventLoop();

    /**
     * connection event loop
     *
     * @return event loop
     */
    GunConnEventLoop connEventLoop();


    /**
     * all of data event loop
     *
     * @return event loop
     */
    GunDataEventLoop[] dataEventLoop();


    /**
     * stop all of event loop
     */

    void shutDown();


}
