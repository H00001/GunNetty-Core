package top.gunplan.netty;

import com.sun.istack.internal.NotNull;
import top.gunplan.netty.protocol.GunNetRequestInterface;


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

    private GunNetRequestInterface object;

    GunRequestFilterDto(@NotNull byte[] src) {
        this.src = src;
        this.object = null;
    }

    public GunNetRequestInterface getObject() {
        return object;
    }

    public GunRequestFilterDto(@NotNull byte[] src, GunNetRequestInterface object) {
        this.src = src;
        this.object = object;
    }

    public byte[] getSrc() {
        return src;
    }

    public GunNetRequestInterface getGunRequestProtoclObject() {
        return object;
    }

    public void setObject(GunNetRequestInterface object) {
        this.object = object;
    }
}
