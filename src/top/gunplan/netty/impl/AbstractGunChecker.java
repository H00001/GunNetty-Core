package top.gunplan.netty.impl;

import java.nio.channels.SelectionKey;

/**
 * AbstractGunChecker checker transfer object
 *
 * @author dosdrtt
 * @see GunNettyChecker
 */
abstract class AbstractGunChecker<Transfer extends GunNetBound> implements GunNettyChecker, GunNettyTranslator {

    byte[] src;

    Transfer to;
    private Object attach;

    /**
     * translate
     *
     * @see GunNetBound
     */
    @Override
    public abstract void translate();


    /**
     * get the source
     *
     * @return bytes
     */
    @Override
    public byte[] source() {
        return src;
    }

    @Override
    public void setSource(byte[] src) {
        this.src = src;
    }


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

    @Override
    public void setKey(SelectionKey key) {
        this.key = key;
    }

    AbstractGunChecker(SelectionKey key) {
        this.key = key;
    }

    @Override
    public Object attach() {
        return attach;
    }

    @Override
    public void attach(Object attach) {
        this.attach = attach;
    }



}
