/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty.impl;

import java.nio.channels.SelectionKey;

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
    SelectionKey getKey();


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
    void setKey(SelectionKey key);


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

}
