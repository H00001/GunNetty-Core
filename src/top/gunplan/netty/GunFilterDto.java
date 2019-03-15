package top.gunplan.netty;

import com.sun.istack.internal.NotNull;
import top.gunplan.netty.filters.GunRequestProtocl;


/**
 * @author dosdrtt
 */
public class GunFilterDto {
    private final byte[] src;
    private GunRequestProtocl object;

    GunFilterDto(@NotNull byte[] src) {
        this.src = src;
        this.object = null;
    }

    public GunFilterDto(@NotNull byte[] src, GunRequestProtocl object) {
        this.src = src;
        this.object = object;
    }

    public byte[] getSrc() {
        return src;
    }

    public GunRequestProtocl getGunRequestProtoclObject() {
        return object;
    }

    public void setObject(GunRequestProtocl object) {
        this.object = object;
    }
}
