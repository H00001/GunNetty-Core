/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.buffer;

/**
 * GunNettySafeBuffer
 *
 * @author frank albert
 * @version 0.0.0.2
 */
public class GunNettySafeBuffer extends BaseGunNettyBuffer {


    private final byte[] save;

    public GunNettySafeBuffer(int len) {
        super(len);
        save = new byte[len];
    }


    @Override
    public void write(byte[] bin) {
    }

    @Override
    public void write(byte bin) {

    }

    @Override
    public byte read() {
        return 0;
    }

    @Override
    public byte[] read(int len) {
        return new byte[0];
    }

    @Override
    public void flushData() {

    }

}
