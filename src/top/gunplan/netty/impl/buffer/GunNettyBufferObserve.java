/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.buffer;

/**
 * GunNettyBufferObserve
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-06-08 15:17
 */
@FunctionalInterface
public interface GunNettyBufferObserve {
    /**
     * on release execute
     *
     * @param stream GunNettyBufferStream
     */
    void onRelease(GunNettyBufferStream stream);
}
