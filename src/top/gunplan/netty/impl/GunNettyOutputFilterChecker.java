package top.gunplan.netty.impl;

import top.gunplan.netty.protocol.GunNetOutBound;

import java.nio.channels.SelectionKey;

/**
 * @author dosdrtt
 * @see GunNettyChecker
 */
public final class GunNettyOutputFilterChecker extends AbstractGunChecker<GunNetOutBound> {

    /**
     * GunNettyOutputFilterChecker
     *
     * @param outputObject GunNetOutBound
     * @param key          SelectionKey
     */
    public GunNettyOutputFilterChecker(GunNetOutBound outputObject, SelectionKey key) {
        super(key);
        this.to = outputObject;
    }


    public GunNettyOutputFilterChecker(GunNetOutBound outputObject) {
        super(null);
        this.to = outputObject;
    }

    public GunNettyOutputFilterChecker() {
        super(null);
    }


    @Override
    public void translate() {
        this.src = to.serialize();
    }
}
