/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.impl.GunNettyChannel;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;

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
public interface GunNettyChildChannel<CH extends Channel> extends GunNettyChannel<CH, GunDataEventLoop<SocketChannel>> {
    /**
     * close channel and remove form selector
     */
    void closeAndRemove();


    /**
     * parent
     * return parent
     */

    GunNettyServerChannel parent();


    /**
     * setSelectionKey
     *
     * @param key selection key
     * @return channel chain style
     */
    GunNettyChildChannel<CH> setSelectionKey(SelectionKey key);


    GunNettyChildChannel<CH> addReadObserve();


    GunNettyChildChannel<CH> cleanAllObserve();


    void continueLoop();
}
