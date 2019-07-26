package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.GunTimeExecutor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.LongAdder;


/**
 * AbstractGunTimeExecutor
 *
 * @author dosdrtt
 * @date 2019/05/30
 */
public abstract class AbstractGunTimeExecutor implements GunTimeExecutor {
    volatile List<GunNettyTimer> works = new CopyOnWriteArrayList<>();
    volatile LongAdder sum = new LongAdder();
    volatile GunNettyCoreThreadManager manager;

    @Override
    public GunTimeExecutor registerManager(GunNettyCoreThreadManager manager) {
        this.manager = manager;
        return this;
    }

    /**
     * when event loop execute
     */
    @Override
    public abstract void run();

    @Override
    public GunTimeExecutor registerWorker(List<GunNettyTimer> works) {
        this.works.addAll(works);
        return this;
    }

    @Override
    public GunTimeExecutor registerWorker(GunNettyTimer work) {
        this.works.add(work);
        return this;
    }

    @Override
    public GunTimeExecutor eraserWorker(GunNettyTimer work) {
        this.works.remove(work);
        return this;
    }
}
