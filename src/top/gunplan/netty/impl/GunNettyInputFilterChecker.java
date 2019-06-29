package top.gunplan.netty.impl;

import top.gunplan.netty.protocol.GunNetInputInterface;

import java.nio.channels.SelectionKey;


/**
 * GunNettyInputFilterChecker
 *
 * @author dosdrtt
 */
public final class GunNettyInputFilterChecker extends AbstractGunChecker<GunNetInputInterface> {


    GunNettyInputFilterChecker(final SelectionKey key) {
        super(key);
    }


    @Override
    public void translate() {
        this.to.unSerialize(src);
    }

    public GunNettyInputFilterChecker(byte[] src, GunNetInputInterface object) {
        super(null);
        this.src = src;
        this.to = object;
    }

}
