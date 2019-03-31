package top.gunplan.netty.impl;

import top.gunplan.netty.*;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.common.GunNettyProperty;
import top.gunplan.nio.utils.AbstractGunBaseLogUtil;
import top.gunplan.nio.utils.GunBytesUtil;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * @author Gunplan
 * @version 0.0.0.6
 * @apiNote 0.0.0.5
 * @since 0.0.0.4
 */

final class GunBootServerImpl implements GunBootServer {

    private volatile boolean runnable = false;

    private volatile ExecutorService acceptExector;

    private volatile ExecutorService requestExector;

    private volatile GunNetHandle dealhander = null;

    private final List<GunNettyFilter> filters = new CopyOnWriteArrayList<>();

    GunBootServerImpl() {
    }


    @Override
    public boolean isRunnable() {
        return this.runnable;

    }


    @Override
    public synchronized GunBootServer setHandel(GunNetHandle handel) {
        if (handel != null) {
            this.getAnnoAndInsert(handel);
        } else {
            throw new GunException("GunNetHandle is null");
        }
        return this;
    }

    @Override
    public GunBootServer addFilter(GunNettyFilter filter) {
        GunNetFilterOrder order = filter.getClass().getAnnotation(GunNetFilterOrder.class);
        this.filters.add(order.index(), filter);
        return this;
    }


    private void getAnnoAndInsert(GunNetHandle handle) {
        this.dealhander = handle;
    }

    @Override
    public void inintObject(Class<? extends GunHandle> clazz) throws IllegalAccessException, InstantiationException {
        if (clazz != null) {
            GunHandle h = clazz.newInstance();
            if (h instanceof GunNetHandle) {
                getAnnoAndInsert((GunNetHandle) h);
            }
        }
    }

    private boolean initCheck() {
        return this.acceptExector != null && requestExector != null && this.dealhander != null && !runnable;
    }


    @Override
    public synchronized void sync() throws ExecutionException, InterruptedException {
        AbstractGunBaseLogUtil.setLevel(0);
        AbstractGunBaseLogUtil.debug("A high performance net server and a reverse proxy server");
        AbstractGunBaseLogUtil.outputFile("banner.txt");
        if (!this.initCheck() || !GunNettyProperty.getProperty()) {
            throw new GunException("handel, execute pool not set or has been running");
        }
        GunBytesUtil.init(GunNettyProperty.getFileReadBufferMin());
        AbstractGunBaseLogUtil.debug("Check parameters succeed");
        if (CoreThreadManage.init(acceptExector, requestExector, filters, dealhander, GunNettyProperty.getPort())) {
            Future<Integer> result = CoreThreadManage.startAllAndWait();
            result.get();
        }
    }


    @Override
    public GunBootServer setExecuters(ExecutorService acceptExecuters, ExecutorService requestExecuters) {
        this.acceptExector = acceptExecuters;
        this.requestExector = requestExecuters;
        return this;
    }

}
