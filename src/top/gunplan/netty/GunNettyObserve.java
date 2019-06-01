package top.gunplan.netty;

import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;

/**
 * GunNettyObserve
 *
 * @author dosdrtt
 * @since 0.0.1.2
 */
public interface GunNettyObserve extends GunHandle {
    /**
     * onBooted execute
     * @param property Boot property
     */
    void onBooted(GunNettyCoreProperty property);

    /**
     * onBooting execute
     * @param property GunPropertyMap
     * @return can or not can boot
     */

    boolean onBooting(GunNettyCoreProperty property);

    /**
     * onStop execute
     * @param property Stop proPerty
     */
    void onStop(GunNettyCoreProperty property);

    /**
     * @param status changed status
     */
    void onStatusChanged(GunNettyStatus status);

    enum GunNettyStatus {
        /**
         * RUNTOSTOP run status to stop status
         */
        RUNTOSTOP, STOPTORUN
    }
}
