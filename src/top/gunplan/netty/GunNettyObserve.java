package top.gunplan.netty;

import top.gunplan.netty.impl.propertys.GunProPerty;

/**
 * GunNettyObserve
 * @author dosdrtt
 * @since 0.0.1.2
 */
public interface GunNettyObserve extends GunHandle {
    /**
     *
     * @param proPerty Boot proPerty
     */
    void onBoot(GunProPerty proPerty);

    /**
     *
     * @param proPerty Stop proPerty
     */
    void onStop(GunProPerty proPerty);

    /**
     *
     * @param status changed status
     */
    void onStatusChanged(GunNettyStatus status);

    public enum GunNettyStatus {
        RUNTOSTOP,STOPTORUN
    }
}
