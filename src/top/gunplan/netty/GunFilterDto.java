package top.gunplan.netty;

import com.sun.istack.internal.NotNull;


public class GunFilterDto {
    private final byte[] src;
    private Object object;

    public GunFilterDto(@NotNull byte[] src)
    {
        this.src = src;
        this.object = null;
    }

    public GunFilterDto(@NotNull byte[] src, Object object) {
        this.src = src;
        this.object = object;
    }

    public byte[] getSrc() {
        return src;
    }



    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
