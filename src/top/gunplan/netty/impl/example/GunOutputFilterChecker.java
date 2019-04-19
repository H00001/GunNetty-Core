package top.gunplan.netty.impl.example;

import top.gunplan.netty.protocol.GunNetOutputInterface;

import java.nio.channels.SelectionKey;

/**
 * @author dosdrtt
 */
public final class GunOutputFilterChecker {

    private GunNetOutputInterface respobj;

    private SelectionKey key;

    /**
     * GunOutputFilterChecker
     * @param respobj GunNetOutputInterface
     * @param key SelectionKey
     */
    public GunOutputFilterChecker(GunNetOutputInterface respobj, SelectionKey key) {
        this.respobj = respobj;
        this.key = key;
    }

    public SelectionKey getKey() {
        return key;
    }

    public void setKey(SelectionKey key) {
        this.key = key;
    }

    public GunNetOutputInterface getRespobj() {
        return respobj;
    }

    public void setRespobj(GunNetOutputInterface respobj) {
        this.respobj = respobj;
    }

    public GunOutputFilterChecker(GunNetOutputInterface respobj) {
        this.respobj = respobj;
    }

    public GunOutputFilterChecker() {
    }
}
