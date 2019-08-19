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
 * @date 2019-07-26 23:30
 */

public class GunString implements GunNetInBoundOutBound {
    private static int v = 0;
    private String base;

    @Override
    public boolean unSerialize(byte[] in) {
        base = new String(in) + ":" + v++;
        return true;
    }

    @Override
    public byte[] serialize() {
        return base.getBytes();
    }
}
