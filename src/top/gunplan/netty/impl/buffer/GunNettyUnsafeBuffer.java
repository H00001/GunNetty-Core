package top.gunplan.netty.impl.buffer;

import sun.misc.Unsafe;
import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionTypes;

class GunNettyUnsafeBuffer extends BaseGunNettyUnsafeBuffer {


    GunNettyUnsafeBuffer(long memoryAddress, int len, Unsafe unsafe) {
        super(memoryAddress, len, unsafe);
    }

    @Override
    public void write(byte[] bin) {
        if (maxlen - writePoint > bin.length) {
            for (byte b : bin) {
                write(b);
            }
        } else {
            throw new GunException(GunExceptionTypes.OUT_POSITION, GunNettyUnsafeBuffer.class.getSimpleName());
        }
    }

    @Override
    public void write(byte bin) {
        if (maxlen - writePoint >= 1) {
            unsafe.putByte(memorySegmentAddress + writePoint, bin);
            writePoint++;
        } else {
            throw new GunException(GunExceptionTypes.OUT_POSITION, GunNettyUnsafeBuffer.class.getSimpleName());
        }
    }

    @Override
    public byte read() {
        byte b;
        if (maxlen - readPoint >= 1) {
            b = unsafe.getByte(memorySegmentAddress + readPoint);
            readPoint++;
        } else {
            throw new GunException(GunExceptionTypes.OUT_POSITION, GunNettyUnsafeBuffer.class.getSimpleName());
        }
        return b;
    }

    @Override
    public byte[] read(int len) {
        byte[] b = new byte[len];
        if (maxlen - readPoint > len) {
            for (int i = 0; i < len; i++) {
                b[i] = read();
            }
        } else {
            throw new GunException(GunExceptionTypes.OUT_POSITION, GunNettyUnsafeBuffer.class.getSimpleName());
        }
        return b;
    }


}
