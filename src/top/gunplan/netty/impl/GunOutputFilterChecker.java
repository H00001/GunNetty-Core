package top.gunplan.netty.impl;

import top.gunplan.netty.protocol.GunNetOutputInterface;

import java.nio.channels.SelectionKey;

/**
 * @author dosdrtt
 * @see top.gunplan.netty.impl.GunChecker
 */
public final class GunOutputFilterChecker extends AbstractGunChecker {

    private GunNetOutputInterface respobj;


    /**
     * GunOutputFilterChecker
     *
     * @param respobj GunNetOutputInterface
     * @param key     SelectionKey
     */
    public GunOutputFilterChecker(GunNetOutputInterface respobj, SelectionKey key) {
        super(key);
        this.respobj = respobj;
    }


    public GunNetOutputInterface getRespobj() {
        return respobj;
    }

    public void setRespobj(GunNetOutputInterface respobj) {
        this.respobj = respobj;
    }

    public GunOutputFilterChecker(GunNetOutputInterface respobj) {
        super(null);
        this.respobj = respobj;
    }

    public GunOutputFilterChecker() {
        super(null);
    }
}
