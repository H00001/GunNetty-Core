/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionMode;

/**
 * GunReadPropertyException
 *
 * @author frank albert
 * @version 0.0.0.1
 */

class GunReadPropertyException extends GunException {
    private static final long serialVersionUID = -4981037343400823114L;

    GunReadPropertyException(String why) {
        super(GunExceptionMode.READ_PROPERTY_ERROR, why);
    }
}
