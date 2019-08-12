/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.GunNettyBaseObserve;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.property.GunNettyCoreProperty;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * GunNettyCoreThreadManager
 *
 * @author frank albert
 * @version 0.0.h.1
 * @date 2019-07-21 15:18
 */
public interface GunNettyCoreThreadManager {
    /**
     * initInstance
     *
     * @param observe  event observe
     * @param property core property
     * @return GunNettyCoreThreadManager
     */
    static GunNettyCoreThreadManager initInstance(final GunNettyCoreProperty property, final GunNettyBaseObserve observe) {
        return new GunNettyCoreThreadManageImpl(property, observe);
    }

    /**
     * dealChannelEventLoop
     * is data thread
     * who can deal channel event? only Data EventLoop
     *
     * @return GunDataEventLoop
     */
    GunDataEventLoop<SocketChannel> dealChannelEventLoop();


    /**
     * stop all and wait for stop
     *
     * @return result
     * @throws InterruptedException when something happened
     */
    boolean stopAndWait() throws InterruptedException;



    /**
     * boot server
     *
     * @return future result
     */
    Future<Integer> startAndWait();



    /**
     * init
     *
     * @param acceptExecutor executor to deal accept event
     * @param dataExecutor   executor to deal data event
     * @param pipeline       pipeline deal handle
     * @param port           listen the port
     * @return init result
     */
    boolean init(ExecutorService acceptExecutor, ExecutorService dataExecutor, ChannelInitHandle handle, ChannelInitHandle f, GunNettyPipeline pipeline, int port);


    /**
     * running state
     *
     * @return manage state
     */
    ManagerState runState();
}

