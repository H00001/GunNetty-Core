package top.gunplan.netty;


import top.gunplan.netty.impl.GunSyncGunInforHander;

import java.util.List;

/**
 *
 */
public interface GunInforHander extends GunH {
    /**
     * @param l GunBootServer.GunNetHander
     * @param t GunBootServer.GunNetHander.EventType
     * @param c GunBootServer.GunNettyRequestOnject
     */
    void doInformate(List<GunBootServer.GunNetHander> l, GunBootServer.GunNetHander.EventType t, GunBootServer.GunNettyRequestOnject c);

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
