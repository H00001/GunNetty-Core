/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty.impl.property;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionType;

/**
 * GunReadPropertyException
 *
 * @author frank albert
 * @version 0.0.0.1
 */

class GunReadPropertyException extends GunException {
    private static final long serialVersionUID = -4981037343400823114L;

    GunReadPropertyException(String why) {
        super(GunExceptionType.READ_PROPERTY_ERROR, why);
    }
}
