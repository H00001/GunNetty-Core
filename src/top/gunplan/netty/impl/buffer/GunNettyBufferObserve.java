/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.buffer;

import top.gunplan.netty.observe.GunObserve;

/**
 * GunNettyBufferObserve
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-06-08 15:17
 */
@FunctionalInterface
public interface GunNettyBufferObserve extends GunObserve {
    /**
     * on release execute
     *
     * @param stream GunNettyBufferStream
     */
    void onRelease(GunNettyBufferStream stream);
}
