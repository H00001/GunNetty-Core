package top.gunplan.netty.impl.example;

import top.gunplan.netty.protocol.GunNetResponseInterface;

import java.nio.channels.SelectionKey;

public final class GunOutputFilterDto {
    private GunNetResponseInterface respobj;
    private SelectionKey key;

    public GunOutputFilterDto(GunNetResponseInterface respobj, SelectionKey key) {
        this.respobj = respobj;
        this.key = key;
    }

    public SelectionKey getKey() {
        return key;
    }

    public void setKey(SelectionKey key) {
        this.key = key;
    }

    public GunNetResponseInterface getRespobj() {
        return respobj;
    }

    public void setRespobj(GunNetResponseInterface respobj) {
        this.respobj = respobj;
    }

    public GunOutputFilterDto(GunNetResponseInterface respobj) {
        this.respobj = respobj;
    }

    public GunOutputFilterDto() {
    }
}
