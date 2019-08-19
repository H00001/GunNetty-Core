/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.GunNettyChannelObserve;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;

import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
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
    void closeAndRemove(boolean b);

    /**
     * parent
     * return parent channel
     */
    GunNettyServerChannel parent();


    /**
     * register ReadWith EventLoop
     *
     * @param eventLoop event loop
     */
    void registerReadWithEventLoop(GunDataEventLoop<SocketChannel> eventLoop);


    GunNettyChildChannel<SocketChannel> addReadObserve(GunNettyChannelObserve observe);

    /**
     * addReadObserve
     *
     * @return this chain style
     */
    GunNettyChildChannel<CH> cleanAllObserve();


    void recoverReadInterest();

    void setKey(SelectionKey key);
}
