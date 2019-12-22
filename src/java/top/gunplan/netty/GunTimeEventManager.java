/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

/**
 * GunTimeEventManager
 *
 * @author frank albert
 * @version 0.0.0.1
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

    /**
     * add thread Timer
     *
     * @param timer doTime event
     * @return server
     */
    GunTimeEventManager addThreadTimer(GunNettyTimer timer);

    void removeThreadTimer();

    /**
     * loop for deal event
     */
    void loop();


    /**
     * boot ,boot the event manager
     *
     * @param var1 interval time
     * @param var2 init wait time
     * @return boot code
     */
    int boot(long var1, long var2);


    /**
     * stop the event
     *
     * @return stop code
     */
    int stop();


    /**
     * acquire the Thread's timer
     *
     * @return timer
     */
    GunNettyTimer acquireThreadTimer();

}
