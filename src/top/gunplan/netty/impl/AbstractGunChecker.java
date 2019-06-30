package top.gunplan.netty.impl;

import java.nio.channels.SelectionKey;

/**
 * AbstractGunChecker checker transfer object
 *
 * @author dosdrtt
 * @see GunNettyChecker
 */
abstract class AbstractGunChecker<Transfer extends GunNetInputOutputInterface> implements GunNettyChecker, GunNettyTranslator {


    byte[] src;

    /**
     * @see GunNetInputOutputInterface
     */
    @Override
    public abstract void translate();


    /**
     * get the source
     *
     * @return bytes
     */
    public byte[] source() {
        return src;
    }

    public void setSource(byte[] src) {
        this.src = src;
    }

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
