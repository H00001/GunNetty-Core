package top.gunplan.netty;


import top.gunplan.netty.impl.GunSyncGunInforHander;

import java.util.List;

/**
 *
 */
public interface GunInforHander extends GunHandel {
    /**
     * @param l GunBootServer.GunNetHandel
     * @param t GunBootServer.GunNetHandel.EventType
     * @param c GunBootServer.GunNettyRequestOnject
     */
    void doInformate(List<GunBootServer.GunNetHandel> l, GunBootServer.GunNetHandel.EventType t, GunBootServer.GunNettyRequestOnject c);

    /**
     *
     * @return GunInforHander
     */
    @Deprecated
    static GunInforHander getAsyncHander() {
       // return new GunAsyncGunInforHander();
        return null;
    }

    /**
     *
     * @return GunInforHander
     */
    @Deprecated
    static GunInforHander getSyncHander() {
        return new GunSyncGunInforHander();
    }
}
