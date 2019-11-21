
/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.protocol;


import top.gunplan.netty.impl.GunNetBound;

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
    byte[] serialize();

}

