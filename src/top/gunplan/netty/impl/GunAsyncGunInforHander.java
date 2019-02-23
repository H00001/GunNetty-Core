package top.gunplan.netty.impl;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunInforHander;
import top.gunplan.netty.anno.AutoWired;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GunAsyncGunInforHander implements GunInforHander {


    public GunAsyncGunInforHander() {
    }


    @AutoWired(classname = "com")
    private ExecutorService deal = Executors.newFixedThreadPool(8);
    private ExecutorService conn = Executors.newFixedThreadPool(8);

    @Override
    public void doInformate(List<GunBootServer.GunNetHander> l, GunBootServer.GunNetHander.EventType t, GunBootServer.C3B4DTO c) {

        if (t == GunBootServer.GunNetHander.EventType.CONNRCTED) {
            deal.execute(new GunBootServer.GunWorker(l, t, c));
        } else {
            conn.execute(new GunBootServer.GunWorker(l, t, c));
        }

    }
}
