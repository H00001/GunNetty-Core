package top.gunplan.netty.impl.example;

import top.gunplan.netty.protocol.GunNetResponseInterface;

public final class GunResponseFilterDto {
    private GunNetResponseInterface respobj;

    public GunNetResponseInterface getRespobj() {
        return respobj;
    }

    public void setRespobj(GunNetResponseInterface respobj) {
        this.respobj = respobj;
    }

    public GunResponseFilterDto(GunNetResponseInterface respobj) {
        this.respobj = respobj;
    }

    public GunResponseFilterDto() {
    }
}
