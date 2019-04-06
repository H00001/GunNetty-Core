package top.gunplan.netty.impl;

import com.sun.istack.internal.NotNull;
import top.gunplan.netty.protocol.GunNetInputInterface;


/**
 * @author dosdrtt
 */
public final class GunRequestFilterDto {
    private byte[] src;
    private boolean writeable = true;

    public void setSrc(byte[] src) {
        this.src = src;
    }

    public boolean isWriteable() {
        return writeable;
    }

    public void setWriteable(boolean writeable) {

        this.writeable = writeable;
    }

    private GunNetInputInterface object;

    public GunRequestFilterDto(@NotNull byte[] src) {
        this.src = src;
        this.object = null;
    }

    public GunNetInputInterface getObject() {
        return object;
    }

    public GunRequestFilterDto(@NotNull byte[] src, GunNetInputInterface object) {
        this.src = src;
        this.object = object;
    }

    public byte[] getSrc() {
        return src;
    }

    public GunNetInputInterface getGunRequestProtoclObject() {
        return object;
    }

    public void setObject(GunNetInputInterface object) {
        this.object = object;
    }
}
