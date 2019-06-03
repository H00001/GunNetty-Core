package top.gunplan.netty.impl;

import java.nio.channels.SelectionKey;

/**
 * GunChecker
 * Checker filters's transfer Object interface
 * @author dosdrtt
 * @version 0.0.0.1
 */
public interface GunChecker {
    /**
     * getKey
     * get selectionKey
     * @return SelectionKey
     */
    SelectionKey getKey();
}
