package top.gunplan.netty;

import top.gunplan.netty.impl.GunNettyManagerGetter;
import top.gunplan.netty.impl.GunNettyTimeExecuteImpl;

import java.util.List;

/**
 * @author dosdrtt
 * @date 2019/05/30
 */

public interface GunTimeExecutor extends Runnable, GunNettyManagerGetter<GunTimeExecutor> {
    /**
     * run
     */
    @Override
    void run();

    static GunTimeExecutor instance() {
        return new GunNettyTimeExecuteImpl();
    }

    /**
     * registerWorker
     * <p>
     * register Worker
     *
     * @param works list of work
     * @return chain style
     */
    GunTimeExecutor registerWorker(List<GunNettyTimer> works);

    /**
     * register work
     *
     * @param work work
     */
    GunTimeExecutor registerWorker(GunNettyTimer work);

    /**
     * delete worker form time execute
     */
    GunTimeExecutor eraserWorker(GunNettyTimer work);


}
