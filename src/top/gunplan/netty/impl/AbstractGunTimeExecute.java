package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.GunTimeExecute;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.LongAdder;


/**
 * @author dosdrtt
 * @date 2019/05/30
 */
public abstract class AbstractGunTimeExecute implements GunTimeExecute {
    volatile List<GunNettyTimer> works = new CopyOnWriteArrayList<>();
    volatile LongAdder sum = new LongAdder();
    volatile GunNettyCoreThreadManager manager;

    @Override
    public GunTimeExecute registerManager(GunNettyCoreThreadManager manager) {
        this.manager = manager;
        return this;
    }

    /**
     * when event loop execute
     */
    @Override
    public abstract void run();

    @Override
    public GunTimeExecute registerWorker(List<GunNettyTimer> works) {
        this.works.addAll(works);
        return this;
    }

    @Override
    public GunTimeExecute registerWorker(GunNettyTimer work) {
        this.works.add(work);
        return this;
    }

    @Override
    public GunTimeExecute eraserWorker(GunNettyTimer work) {
        this.works.remove(work);
        return this;
    }
}
