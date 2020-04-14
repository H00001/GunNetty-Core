/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.filter.GunNettyFilter;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.function.Consumer;

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
     * @param b is or not remove sum of selector
     * @return this
     */
    GunNettyChannel<CH, GunDataEventLoop<SocketChannel>, GunNettyChildrenPipeline> closeAndRemove(boolean b);

    /**
     * generalClose
     * close channel and remove form selector
     * @return Deal Result
     */
    GunNettyFilter.DealResult generalClose();


    /**
     * parent
     *
     * @return parent channel
     */
    <PCH extends ServerSocketChannel> GunNettyServerChannel<PCH> parent();


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


    void sendMessage(ByteBuffer byteBuffer) throws IOException;

    /**
     * pushEvent
     * push event into event queue
     *
     * @param event need to input
     * @return push result
     */
    boolean pushEvent(Object event);

    /**
     * key
     * @return selection key
     */
    SelectionKey key();

    /**
     * when read complete handle
     * @param t call back pointer
     */
    void setWhenReadCompleteCallBack(Consumer<GunNettyChildChannel<CH>> t);
}
