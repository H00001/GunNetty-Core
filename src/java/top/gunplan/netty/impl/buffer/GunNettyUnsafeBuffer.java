/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.buffer;


import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionType;

class GunNettyUnsafeBuffer extends BaseGunNettyUnsafeBuffer {
    public GunNettyUnsafeBuffer(int len) {
        super(len);
    }


//    GunNettyUnsafeBuffer(long memoryAddress, int len, Unsafe unsafe) {
//        super(memoryAddress, len, unsafe);
//    }

    @Override
    public void write(byte[] bin) {
        if (maxLen - writePoint > bin.length) {
            for (byte b : bin) {
                write(b);
            }
        } else {
            throw new GunException(GunExceptionType.OUT_POSITION, GunNettyUnsafeBuffer.class.getSimpleName());
        }
    }

    @Override
    public void write(byte bin) {
        // if (maxLen - writePoint >= 1) {
        //   unsafe.putByte(memorySegmentAddress + writePoint, bin);
        writePoint++;
        // } else {
        throw new GunException(GunExceptionType.NOT_SUPPORT, GunNettyUnsafeBuffer.class.getSimpleName());
        // }
    }

    @Override
    public byte read() {
        //    byte b;
        //    if (maxLen - readPoint >= 1) {
        //     b = unsafe.getByte(memorySegmentAddress + readPoint);
        //       readPoint++;
        //   } else {
            throw new GunException(GunExceptionType.OUT_POSITION, GunNettyUnsafeBuffer.class.getSimpleName());
        //  }
        // return b;
    }

    @Override
    public byte[] read(int len) {
        byte[] b = new byte[len];
        if (maxLen - readPoint > len) {
            for (int i = 0; i < len; i++) {
                b[i] = read();
            }
        } else {
            throw new GunException(GunExceptionType.OUT_POSITION, GunNettyUnsafeBuffer.class.getSimpleName());
        }
        return b;
    }


}
