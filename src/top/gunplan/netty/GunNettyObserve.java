package top.gunplan.netty;

import top.gunplan.netty.impl.propertys.GunProPerty;

public interface GunNettyObserve extends GunHandle {
    void onBoot(GunProPerty proPerty);

    void onStop(GunProPerty proPerty);

    void onStatusChanged(GunNettyStatus status);

    public enum GunNettyStatus {
        RUNTOSTOP,STOPTORUN
    }
}
