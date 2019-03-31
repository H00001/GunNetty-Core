package top.gunplan.netty.impl;

import top.gunplan.netty.GunNetHandle;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.common.GunNettyProperty;
import top.gunplan.nio.utils.AbstractGunBaseLogUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author dosdrtt
 */
public final class CoreThreadManage {
    private static final int MANAGE_THREAD_NUM = GunNettyProperty.getMaxRunnningNum();
    private volatile static AbstractGunCoreEventLoop dealaccept = null;
    private volatile static AbstractGunCoreEventLoop[] dealdata;

    static {
        dealdata = new AbstractGunCoreEventLoop[MANAGE_THREAD_NUM];
    }

    public static volatile ExecutorService server = Executors.newFixedThreadPool(MANAGE_THREAD_NUM ^ 1);
    private static int slelctSelctor = 0;

    static boolean init(ExecutorService acceptExector, ExecutorService dataExectuor, final List<GunNettyFilter> filters, GunNetHandle dealhander, int port) {
        AbstractGunBaseLogUtil.debug("server runnning on " + port);

        try {
            dealaccept = new CunCoreConnetcionThread(acceptExector, dealhander, port);
            for (int i = 0; i < MANAGE_THREAD_NUM; i++) {
                dealdata[i] = new CunCoreDataEventLoop(dataExectuor, filters, dealhander);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static AbstractGunCoreEventLoop getDealThread() {
        return dealdata[slelctSelctor++ & (MANAGE_THREAD_NUM - 1)];
    }

    static Future<Integer> startAllAndWait() {
        for (AbstractGunCoreEventLoop dat : dealdata) {
            server.submit(dat);
        }
        return server.submit(dealaccept, 1);
    }
}
