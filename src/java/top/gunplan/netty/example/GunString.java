/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.example;

import top.gunplan.netty.impl.GunNetInBoundOutBound;

/**
 * GunString
 *
 * @author frank albert
 * @version 0.0.0.1
 */

public class GunString implements GunNetInBoundOutBound {
    private static int v = 0;
    private String base;

    public GunString() {
    }

    public GunString(String base) {
        this.base = base;
    }

    @Override
    public boolean unSerialize(byte[] in) {
        //base = GunTencertZip.doDecode(new String(in));
        base = new String(in);
        return true;
    }

    public String get() {
        return base;
    }

    private void swap(byte[] bytes, int i, int j) {
        bytes[i] = (byte) (bytes[i] ^ bytes[j]);
        bytes[j] = (byte) (bytes[i] ^ bytes[j]);
        bytes[i] = (byte) (bytes[i] ^ bytes[j]);
    }

    @Override
    public byte[] serialize() {
        return base.getBytes();
    }
}
