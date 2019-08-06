/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.protocol.GunProtocolControl;

/**
 * GunNetBound
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-21 10:19
 */

public interface GunNetBound extends GunProtocolControl<Void, Void> {


    @Override
    default Void supply(Void nullper) {
        return null;
    }
}
