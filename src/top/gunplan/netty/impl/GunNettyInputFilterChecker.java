package top.gunplan.netty.impl;

import top.gunplan.netty.protocol.GunNetInbound;

import java.nio.channels.SelectionKey;


/**
 * GunNettyInputFilterChecker
 *
 * @author dosdrtt
 */
public final class GunNettyInputFilterChecker extends AbstractGunChecker<GunNetInbound> {


    GunNettyInputFilterChecker(final SelectionKey key) {
        super(key);
    }


    @Override
    public void translate() {
        this.to.unSerialize(src);
    }

    public GunNettyInputFilterChecker(byte[] src, GunNetInbound object) {
        super(null);
        this.src = src;
        this.to = object;
    }

}
