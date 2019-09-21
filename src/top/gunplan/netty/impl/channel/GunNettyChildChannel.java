/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;

import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;

/**
 * GunNettyChildChannel
 *
 * @author frank albert
 * @version 0.0.0.1
 * # 2019-08-09 22:52
 */
public interface GunNettyChildChannel<CH extends Channel> extends
        GunNettyChannel<CH, GunDataEventLoop<SocketChannel>, GunNettyChildrenPipeline> {
    /**
     * close channel and remove form selector
     *
     * @param b is or not
     * @return this
     */
    GunNettyChannel<CH, GunDataEventLoop<SocketChannel>, GunNettyChildrenPipeline> closeAndRemove(boolean b);

    /**
     * parent
     *
     * @return parent channel
     */
    GunNettyServerChannel parent();


    /**
     * register ReadWith EventLoop
     */
    void registerReadWithEventLoop();





    /**
     * recover Read Interest
     */
    void recoverReadInterest();


    /**
     * consume the event
     *
     * @return an event
     */
    Object consumeEvent();


    /**
     * remove all of event
     * consume the event
     */
    void cleanEvent();


    /**
     * pushEvent
     * push event into event queue
     *
     * @param event need to input
     * @return push result
     */
    boolean pushEvent(Object event);
}
