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



    void setSource(byte[] src);


    void setKey(SelectionKey key);


    /**
     * attached object
     * @param attach object to attach
     */
    void attach(Object attach);


    Object attach();

}
