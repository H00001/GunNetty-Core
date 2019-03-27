package top.gunplan.netty.impl;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunNettyFilter;



import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author dosdrtt
 */
public class CoreThreadManage {
    private static final int MANAGE_THREAD_NUMS = Runtime.getRuntime().availableProcessors();
    private volatile static AbstractGunCoreThread dealaccept = null;
    private volatile static AbstractGunCoreThread[] dealdata = new AbstractGunCoreThread[MANAGE_THREAD_NUMS];
    public static volatile ExecutorService server = Executors.newFixedThreadPool(MANAGE_THREAD_NUMS + 1);
    private static Random random = new Random();

    public static boolean init(ExecutorService acceptExector, ExecutorService dataExectuor, final List<GunNettyFilter> filters, GunBootServer.GunNetHandle dealhander, int port) {
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

    public static AbstractGunCoreThread getDealThread() {
        int val = random.nextInt(Runtime.getRuntime().availableProcessors());

        return dealdata[val];
    }

    public static Future<Integer> startAllAndWait() {
        for (AbstractGunCoreThread thrun : dealdata) {
            server.submit(thrun);
        }
        return server.submit(dealaccept, 1);
    }
}
