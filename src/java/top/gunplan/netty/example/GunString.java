/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.example;

import top.gunplan.netty.impl.GunNetInBoundOutBound;

import java.nio.ByteBuffer;

/**
 * GunString
 *
 * @author frank albert
 * @version 0.0.0.1
 */

public class GunString implements GunNetInBoundOutBound {
    private String base;

    public GunString() {
    }

    public GunString(String base) {
        this.base = base;
    }

    @Override
    public boolean unSerialize(ByteBuffer byteBuffer) {
        base = new String(byteBuffer.array());
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
    public ByteBuffer serialize() {
        byte[] list = base.getBytes();
        return ByteBuffer.allocateDirect(list.length).put(list);
    }
}
