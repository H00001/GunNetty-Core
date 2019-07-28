package top.gunplan.netty.impl;

import top.gunplan.netty.protocol.GunNetOutbound;

import java.nio.channels.SelectionKey;

/**
 * @author dosdrtt
 * @see GunNettyChecker
 */
public final class GunNettyOutputFilterChecker extends AbstractGunChecker<GunNetOutbound> {

    /**
     * GunNettyOutputFilterChecker
     *
     * @param outputObject GunNetOutbound
     * @param key          SelectionKey
     */
    public GunNettyOutputFilterChecker(GunNetOutbound outputObject, SelectionKey key) {
        super(key);
        this.to = outputObject;
    }


    public GunNettyOutputFilterChecker(GunNetOutbound outputObject) {
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
