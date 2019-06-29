package top.gunplan.netty.impl;

import top.gunplan.netty.protocol.GunNetInputInterface;

import java.nio.channels.SelectionKey;


/**
 * @author dosdrtt
 */
public final class GunNettyInputFilterChecker extends AbstractGunChecker<GunNetInputInterface> {


    private byte[] src;
    private boolean writable = true;

    public void setSrc(byte[] src) {
        this.src = src;
    }

    GunNettyInputFilterChecker(final SelectionKey key) {
        super(key);
    }

    public GunNettyInputFilterChecker(byte[] src, GunNetInputInterface object) {
        super(null);
        this.src = src;
        this.to = object;
    }


    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {

        this.writable = writable;
    }

    public byte[] getSrc() {
        return src;
    }
}
