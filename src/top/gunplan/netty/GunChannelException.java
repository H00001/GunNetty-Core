/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import java.io.IOException;

/**
 * GunChannelException
 *
 * @author frank albert
 * @version 0.0.0.3
 * @date 2019-06-30 09:34
 */

public class GunChannelException extends GunException {
    private static final long serialVersionUID = -4413715733291360863L;

    public GunChannelException(GunExceptionType type, String why) {
        super(type, why);
    }

    public GunChannelException(IOException exp) {
        super(GunExceptionType.CHANNEL_ERROR, exp);
    }
}
