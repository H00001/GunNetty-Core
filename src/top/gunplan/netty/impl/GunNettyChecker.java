/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import java.nio.channels.SocketChannel;

/**
 * GunNettyChecker
 * Checker filters's transferTarget Object interface
 *
 * @author dosdrtt
 * @version 0.0.0.2
 */
public interface GunNettyChecker {
    /**
     * getKey
     * get selectionKey
     *
     * @return SelectionKey
     */
    GunNettyChildChannel<SocketChannel> getKey();


    /**
     * source information
     *
     * @return bytes
     */
    byte[] source();


    /**
     * set source
     *
     * @param src source
     */
    void setSource(byte[] src);


    /**
     * set the key
     *
     * @param key selection key
     */
    void setKey(GunNettyChildChannel<SocketChannel> key);


    /**
     * set attached object
     *
     * @param attach object to attach
     */
    void attach(Object attach);

    /**
     * get attached object
     *
     * @return object to attach
     */
    Object attach();


    void setError(Throwable throwable);


    Throwable error();

}