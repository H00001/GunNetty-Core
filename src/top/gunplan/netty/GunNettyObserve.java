package top.gunplan.netty;

import top.gunplan.netty.impl.propertys.GunProperty;

/**
 * GunNettyObserve
 *
 * @author dosdrtt
 * @since 0.0.1.2
 */
public interface GunNettyObserve extends GunHandle {
    /**
     * @param proPerty Boot proPerty
     */
    void onBooted(GunProperty proPerty);


    boolean onBooting(GunProperty proPerty);

    /**
     * @param proPerty Stop proPerty
     */
    void onStop(GunProperty proPerty);

    /**
     * @param status changed status
     */
    void onStatusChanged(GunNettyStatus status);

    public enum GunNettyStatus {
        RUNTOSTOP, STOPTORUN
    }
}
