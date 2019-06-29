package top.gunplan.netty.impl;

import top.gunplan.netty.protocol.GunNetOutputInterface;

import java.nio.channels.SelectionKey;

/**
 * @author dosdrtt
 * @see GunNettyChecker
 */
public final class GunNettyOutputFilterChecker extends AbstractGunChecker<GunNetOutputInterface> {

    /**
     * GunNettyOutputFilterChecker
     *
     * @param outputObject GunNetOutputInterface
     * @param key          SelectionKey
     */
    GunNettyOutputFilterChecker(GunNetOutputInterface outputObject, SelectionKey key) {
        super(key);
        this.to = outputObject;
    }


    GunNettyOutputFilterChecker(GunNetOutputInterface outputObject) {
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
