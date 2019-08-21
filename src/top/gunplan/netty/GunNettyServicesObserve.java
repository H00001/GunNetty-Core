/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.impl.property.GunNettyCoreProperty;

import java.io.IOException;

/**
 * GunNettyObserve
 *
 * @author dosdrtt
 * @since 0.0.1.2
 */
public interface GunNettyServicesObserve extends GunHandle {
    /**
     * onBooted execute
     *
     * @param property Boot property
     */
    void onBooted(GunNettyCoreProperty property);

    /**
     * onBooting execute
     *
     * @param property GunPropertyMap
     * @return can or not can boot
     */

    boolean onBooting(GunNettyCoreProperty property);

    /**
     * onStop execute
     *
     * @param property Stop property
     */
    void onStop(GunNettyCoreProperty property);

    /**
     * onStatusChanged
     * on status  changed happens
     *
     * @param status changed status
     */
    void onStatusChanged(GunNettyChangeStatus status);

    enum GunNettyChangeStatus {
        /**
         * RUN TO STOP run status to stop status
         */
        RUN_TO_STOP, STOP_TO_RUN, RUN_TO_PAUSE
    }


    /**
     * bootFail happened
     *
     * @param exp error
     * @apiNote #4043
     */
    default void bootFail(IOException exp) {
        GunNettyContext.logger.setTAG(this.getClass()).urgency(exp.getMessage());
    }


    /**
     * bootFail happened
     *
     * @param exp error
     * @apiNote #4043
     */
    default void runningError(Exception exp) {
        GunNettyContext.logger.setTAG(this.getClass()).urgency(exp.getMessage());
    }
}
