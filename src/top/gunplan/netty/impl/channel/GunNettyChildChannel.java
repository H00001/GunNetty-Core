/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;
import top.gunplan.netty.observe.GunNettyChannelObserve;

import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;

/**
 * GunNettyChildChannel
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-09 22:52
 */
public interface GunNettyChildChannel<CH extends Channel> extends GunNettyChannel<CH, GunDataEventLoop<SocketChannel>, GunNettyChildrenPipeline> {
    /**
     * close channel and remove form selector
     *
     * @param b is or not
     */
    GunNettyChannel<CH, GunDataEventLoop<SocketChannel>, GunNettyChildrenPipeline> closeAndRemove(boolean b);

    /**
     * parent
     * return parent channel
     */
    GunNettyServerChannel parent();


    /**
     * register ReadWith EventLoop
     */
    void registerReadWithEventLoop();


    GunNettyChildChannel<SocketChannel> addReadObserve(GunNettyChannelObserve observe);

    /**
     * addReadObserve
     *
     * @return this chain style
     */
    GunNettyChildChannel<CH> cleanAllObserve();


    /**
     * recover Read Interest
     */
    void recoverReadInterest();
}
