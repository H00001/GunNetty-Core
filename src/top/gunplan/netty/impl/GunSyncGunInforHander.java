package top.gunplan.netty.impl;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunException;
import top.gunplan.netty.GunInforHander;
import java.util.List;

/**
 *
 * @author dosdrtt
 */
public class GunSyncGunInforHander implements GunInforHander {

    public GunSyncGunInforHander() {

    }

    @Override
    public void doInformate(List<GunBootServer.GunNetHander> l, GunBootServer.GunNetHander.EventType t, GunBootServer.GunNettyRequestOnject c) {
        for (GunBootServer.GunNetHander g : l) {
            try {
                g.dealevent(t, c);
            } catch (Exception e) {
                throw new GunException(e);
            }
        }
    }
}
