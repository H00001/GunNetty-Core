/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.protocol;

import top.gunplan.netty.impl.GunNetBound;

/**
 * GunNetInbound
 *
 * @author dosdrtt
 */
@FunctionalInterface
public interface GunNetInbound extends GunNetBound {
    /**
     * get the unSerialize result succeed or fail
     *
     * @param in byte[]
     * @return serialize status
     */
    boolean unSerialize(byte[] in);
}
