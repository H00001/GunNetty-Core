package top.gunplan.netty.impl;

import java.nio.channels.SelectionKey;

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
