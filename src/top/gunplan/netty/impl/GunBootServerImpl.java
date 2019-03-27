package top.gunplan.netty.impl;

import top.gunplan.netty.*;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.nio.utils.GunBaseLogUtil;
import java.nio.channels.Selector;
import java.util.List;
import java.util.concurrent.*;


/**
 * @author Gunplan
 * @version 0.0.0.6
 * @apiNote 0.0.0.5
 * @since 0.0.0.4
 */

final class GunBootServerImpl implements GunBootServer {

    private final int var3315;

    private volatile boolean runnable = false;

    private Selector bootSelector;

    private ExecutorService acceptExector;

    private ExecutorService requestExector;

    private volatile GunBootServer.GunNetHandle dealhander = null;

    private final List<GunNettyFilter> filters = new CopyOnWriteArrayList<>();

    private GunBootServerImpl() {
        this(GunNettySupportParameter.Companion.getPort());
    }

    @Override
    public AbstractGunCoreThread getADataThread() {
        return null;
    }


    public static AbstractGunCoreThread getADataThread0() {
        return null;
    }

    @Override
    public boolean isRunnable() {
        return this.runnable;

    }


    GunBootServerImpl(int var3315) {
        this.var3315 = var3315;
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


    private void getAnnoAndInsert(GunNetHandle hander) {
        this.dealhander = hander;
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
        GunBaseLogUtil.debug("A high performance net server and a reverse proxy server");
        if (!this.initCheck()) {
            throw new GunException("handel , executepool not set or has been running");
        }
        GunBaseLogUtil.debug("Check parameters succeed");
        if (CoreThreadManage.init(acceptExector, requestExector, filters, dealhander, var3315)) {
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
