package top.gunplan.netty;

import top.gunplan.netty.impl.GunNettyTimeExecuteImpl;

import java.util.List;

/**
 * @author dosdrtt
 * @date 2019/05/30
 */

public interface GunTimeExecute extends Runnable {
    /**
     * run
     */
    @Override
    void run();

    static GunTimeExecute instance() {
        return new GunNettyTimeExecuteImpl();
    }

    /**
     * register Worker
     *
     * @param works list of work
     */
    GunTimeExecute registerWorker(List<GunNettyTimer> works);

    /**
     * register work
     *
     * @param work work
     */
    GunTimeExecute registerWorker(GunNettyTimer work);

    /**
     * delete worker form time execute
     */
    GunTimeExecute eraserWorker(GunNettyTimer work);
}
