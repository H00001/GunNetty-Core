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
    private String base;

    public String getBase() {
        return base;
    }

    @Override
    public boolean unSerialize(byte[] in) {
        base = new String(in);
        return true;
    }

    @Override
    public byte[] serialize() {
        return base.getBytes();
    }
}
