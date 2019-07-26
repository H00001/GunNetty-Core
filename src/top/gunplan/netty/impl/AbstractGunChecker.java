package top.gunplan.netty.impl;

import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.protocol.GunNetInbound;

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


    @Override
    public <R extends GunNetInbound> boolean tranToObject(Class<R> in) {
        try {
            R instance = in.getDeclaredConstructor().newInstance();
            this.to = (Transfer) instance;
            return instance.unSerialize(src);
        } catch (ReflectiveOperationException e) {
            GunNettyContext.logger.error(e);
            return false;
        }

    }
}
