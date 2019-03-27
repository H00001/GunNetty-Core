package top.gunplan.netty.impl;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.nio.utils.GunBaseLogUtil;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CoreThreadManage {
    private volatile static AbstractGunCoreThread dealaccept = null;
    private volatile static AbstractGunCoreThread[] dealdata = new AbstractGunCoreThread[Runtime.getRuntime().availableProcessors()];
    public static volatile ExecutorService server = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    private static Random random = new Random();

    public static boolean init(ExecutorService acceptExector, ExecutorService dataExectuor, final List<GunNettyFilter> filters, GunBootServer.GunNetHandle dealhander, int port) throws IOException {
        try {
            dealaccept = new CunCoreConnetcionThread(acceptExector, dealhander, port);
            for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
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
