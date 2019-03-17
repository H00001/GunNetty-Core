package top.gunplan.netty;

import com.sun.istack.internal.NotNull;
import top.gunplan.netty.protocol.GunRequestProtoclInterface;


/**
 * @author dosdrtt
 */
public class GunFilterDto {
    private byte[] src;

    public void setSrc(byte[] src) {
        this.src = src;
    }

    private GunRequestProtoclInterface object;

    GunFilterDto(@NotNull byte[] src) {
        this.src = src;
        this.object = null;
    }

    public GunRequestProtoclInterface getObject() {
        return object;
    }

    public GunFilterDto(@NotNull byte[] src, GunRequestProtoclInterface object) {
        this.src = src;
        this.object = object;
    }

    public byte[] getSrc() {
        return src;
    }

    public GunRequestProtoclInterface getGunRequestProtoclObject() {
        return object;
    }

    public void setObject(GunRequestProtoclInterface object) {
        this.object = object;
    }
}
