package top.gunplan.netty;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;


/**
 * @author dosdrtt
 * @date 2019/05/30
 */
public abstract class AbstractGunTimeExecute implements GunTimeExecute {
    protected volatile List<GunNettyTimer> works = null;
    protected volatile LongAdder sum = new LongAdder();


    /**
     * when event loop execute
     */
    @Override
    public abstract void run();

    @Override
    public void registerWorker(List<GunNettyTimer> works) {
        this.works = works;
    }

    @Override
    public void addWorker(GunNettyTimer work) {
        assert work != null;
        this.works.add(work);
    }

}
