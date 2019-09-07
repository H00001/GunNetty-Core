/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

/**
 * GunTimeEventManager
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-09-07 12:23
 */
public interface GunTimeEventManager {
    /**
     * addGlobalTimers
     *
     * @param timer doTime event
     * @return this
     */
    GunTimeEventManager addGlobalTimers(GunNettyTimer timer);

    /**
     * remove GlobalTimers
     *
     * @param timer doTime event
     * @return server
     */
    GunTimeEventManager removeGlobalTimers(GunNettyTimer timer);

    void loop();


    int boot(long var1, long var2);


    int stop();
}
