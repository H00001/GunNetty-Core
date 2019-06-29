package top.gunplan.netty.impl;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionTypes;

public class GunBootException extends GunException {
    private boolean canBoot = true;

    public GunBootException(GunExceptionTypes type, boolean canBoot, String why) {
        super(type, why);
        this.canBoot = canBoot;
    }

    public boolean isCanBoot() {
        return canBoot;
    }
}
