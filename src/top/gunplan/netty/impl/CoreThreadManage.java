package top.gunplan.netty.impl;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.common.GunNettyProperty;
import top.gunplan.nio.utils.GunBaseLogUtil;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author dosdrtt
 */
public class CoreThreadManage {
    private static final int MANAGE_THREAD_NUMS = GunNettyProperty.getMaxRunnningNum();
    private volatile static AbstractGunCoreThread dealaccept = null;
    private volatile static AbstractGunCoreThread[] dealdata = new AbstractGunCoreThread[MANAGE_THREAD_NUMS];
    public static volatile ExecutorService server = Executors.newFixedThreadPool(MANAGE_THREAD_NUMS + 1);
    private static int slelctSelctor = 0;

    static boolean init(ExecutorService acceptExector, ExecutorService dataExectuor, final List<GunNettyFilter> filters, GunBootServer.GunNetHandle dealhander, int port) {
        GunBaseLogUtil.debug("server runnning on " + port);

        try {
            dealaccept = new CunCoreConnetcionThread(acceptExector, dealhander, port);
            for (int i = 0; i < MANAGE_THREAD_NUMS; i++) {
                dealdata[i] = new CunCoreDataThread(dataExectuor, filters, dealhander);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static AbstractGunCoreThread getDealThread() {
        return dealdata[slelctSelctor++&(MANAGE_THREAD_NUMS-1)];

    }

    static Future<Integer> startAllAndWait() {
        for (AbstractGunCoreThread dat : dealdata) {
            server.submit(dat);
        }

        return server.submit(dealaccept, 1);
    }
}
