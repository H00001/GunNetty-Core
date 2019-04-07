package top.gunplan.netty.impl.buffer;


import top.gunplan.netty.GunBuffer;

/**
 *
 */
public class GunSafedPulledBuffer implements GunBuffer {
    @Override
    public byte[] malloc(int size, int len) throws Exception {
        return new byte[0];
    }

    @Override
    public byte[] malloc(int len) throws Exception {
        return new byte[0];
    }
}
