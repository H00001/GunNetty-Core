package top.gunplan.netty;

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

    /**
     * register Worker
     *
     * @param works add time works
     */
    void registerWorker(List<GunNettyTimer> works);


    /**
     * add work
     *
     * @param work work
     */
    void addWorker(GunNettyTimer work);
}
