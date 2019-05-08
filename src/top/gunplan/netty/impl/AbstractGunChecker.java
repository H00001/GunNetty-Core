package top.gunplan.netty.impl;

import java.nio.channels.SelectionKey;

/**
 * @author dosdrtt
 * @see top.gunplan.netty.impl.GunChecker
 */
public abstract class AbstractGunChecker implements GunChecker {

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
