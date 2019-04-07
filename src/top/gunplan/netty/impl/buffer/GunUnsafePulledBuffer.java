package top.gunplan.netty.impl.buffer;

import sun.misc.Unsafe;
import top.gunplan.netty.GunBuffer;

public class GunUnsafePulledBuffer implements GunBuffer {
    @Override
    public byte[] malloc(int size, int len) throws Exception {
        final Unsafe unsafe = newUnsafe();
        long address = unsafe.allocateMemory(size * len);
//        unsafe.putby
        return null;
    }

    @Override
    public byte[] malloc(int len) throws Exception {
        return malloc(1, len);
    }
}
