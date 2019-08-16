/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.buffer;

import sun.misc.Unsafe;

/**
 * GunNettyUnsafeSyncBuffer
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 19:12
 */

public class GunNettyUnsafeSyncBuffer extends GunNettyUnsafeBuffer {
    public GunNettyUnsafeSyncBuffer(long memoryAddress, int len, Unsafe unsafe) {
        super(memoryAddress, len, unsafe);
    }


    /**
     * in fact， if the type is int,
     * we do not need to write synchronized
     * keyword, but it is long type ,you'd
     * know the long type was written in 2
     * step from the register to memory :
     * first write 32 high bit the next is
     * write low 32 bits.so if we do not
     * use {@link synchronized}
     *
     * @return long write point
     */

    @Override
    public synchronized long writePoint() {
        return super.writePoint();
    }

    /**
     * in fact if the type is int,
     * we do not need to write synchronized
     * keyword ,but it is long type ,you'd
     * know the long type was written in 2
     * step from the register to memory :
     * first write 32 high bit the next is
     * write low 32 bits
     *
     * @return long read point
     */
    @Override
    public synchronized long readPoint() {
        return super.readPoint();
    }

    @Override
    public synchronized void write(byte bin) {
        super.write(bin);
    }

    @Override
    public synchronized void write(byte[] bin) {
        super.write(bin);
    }

    @Override
    public synchronized byte[] read(int len) {
        return super.read(len);
    }

    @Override
    public synchronized byte read() {
        return super.read();
    }

}
