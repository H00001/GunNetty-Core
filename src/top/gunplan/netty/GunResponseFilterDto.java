package top.gunplan.netty;

import top.gunplan.netty.protocol.GunNetResponseInterface;

public class GunResponseFilterDto {
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
