package top.gunplan.netty;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.LongAdder;


/**
 * @author dosdrtt
 * @date 2019/05/30
 */
public abstract class AbstractGunTimeExecute implements GunTimeExecute {
    protected volatile List<GunNettyTimer> works = new CopyOnWriteArrayList<>();
    protected volatile LongAdder sum = new LongAdder();


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
