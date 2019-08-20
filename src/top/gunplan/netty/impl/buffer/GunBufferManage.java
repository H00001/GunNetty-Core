/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.buffer;


/**
 * @author dosdrtt
 */
public interface GunBufferManage {

    /**
     * get buffer
     * @param size size
     * @return GunNettyBufferStream
     */
    GunNettyBufferStream getBuffer(int size);

    /**
     * usingCount
     *
     * @return int buffer count
     */
    int usingCount();
}
