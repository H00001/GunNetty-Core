package top.gunplan.netty.impl;

import java.nio.channels.SelectionKey;

/**
 * AbstractGunChecker checker transfer object
 *
 * @author dosdrtt
 * @see GunNettyChecker
 */
abstract class AbstractGunChecker<Transfer extends GunNetInputOutputInterface> implements GunNettyChecker {


    Transfer to;
    private volatile SelectionKey key;

    public Transfer getTransfer() {
        return to;
    }

    public void setTransfer(Transfer to) {
        this.to = to;
    }


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
