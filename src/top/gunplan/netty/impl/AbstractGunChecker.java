package top.gunplan.netty.impl;

import java.nio.channels.SelectionKey;

/**
 * AbstractGunChecker checker transfer object
 *
 * @author dosdrtt
 * @see GunNettyChecker
 */
abstract class AbstractGunChecker implements GunNettyChecker {


    private volatile SelectionKey key;

    @Override
    public SelectionKey getKey() {
        return key;
    }

    public void setKey(SelectionKey key) {
        this.key = key;
    }

    AbstractGunChecker(SelectionKey key) {
        this.key = key;
    }

}
