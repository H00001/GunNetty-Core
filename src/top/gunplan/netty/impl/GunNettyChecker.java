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


    byte[] source();


    void setSource(byte[] src);


    void setKey(SelectionKey key);


    void attach(Object attach);


    Object attach();

}
