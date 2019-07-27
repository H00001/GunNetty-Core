package top.gunplan.netty.impl;

import java.nio.channels.SelectionKey;

/**
 * GunNettyChecker
 * Checker filters's transfer Object interface
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
