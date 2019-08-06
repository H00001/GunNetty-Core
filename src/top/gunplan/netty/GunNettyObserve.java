/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty;

import top.gunplan.netty.impl.property.GunNettyCoreProperty;

/**
 * GunNettyObserve
 *
 * @author dosdrtt
 * @since 0.0.1.2
 */
public interface GunNettyObserve extends GunHandle, GunNettyBaseObserve {
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
}
