/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionType;

/**
 * GunBootException
 *
 * @author frank albert
 * @version 0.0.0.1
 * # 2019-06-29 22:50
 */

public class GunBootException extends GunException {
    private boolean canBoot = true;

    public GunBootException(GunExceptionType type, boolean canBoot, String why) {
        super(type, why);
        this.canBoot = canBoot;
    }

    public boolean isCanBoot() {
        return canBoot;
    }
}
