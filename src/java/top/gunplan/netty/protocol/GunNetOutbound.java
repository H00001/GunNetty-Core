
/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.protocol;


import top.gunplan.netty.impl.GunNetBound;

import java.nio.ByteBuffer;

/**
 * GunNetOutbound
 *
 * @author dosdrtt
 */
@FunctionalInterface
public interface GunNetOutbound extends GunNetBound {
    /**
     * serialize the protocol
     *
     * @return bytes[] transferTarget to client
     */
    ByteBuffer serialize();
}

