package top.gunplan.netty;


import top.gunplan.netty.impl.GunAsyncGunInforHander;
import top.gunplan.netty.impl.GunSyncGunInforHander;

import java.util.List;

/**
 *
 */
public interface GunInforHander extends GunH {
    /**
     * @param l GunBootServer.GunNetHander
     * @param t GunBootServer.GunNetHander.EventType
     * @param c GunBootServer.C3B4DTO
     */
    void doInformate(List<GunBootServer.GunNetHander> l, GunBootServer.GunNetHander.EventType t, GunBootServer.C3B4DTO c);

    /**
     *
     * @return GunInforHander
     */
    static GunInforHander getAsyncHander() {
        return new GunAsyncGunInforHander();
    }

    /**
     *
     * @return GunInforHander
     */
    static GunInforHander getSyncHander() {
        return new GunSyncGunInforHander();
    }
}
