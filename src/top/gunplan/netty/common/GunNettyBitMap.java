/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.common;

/**
 * GunNettyBitMap
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-09-01 21:54
 */
public interface GunNettyBitMap {
    /**
     * get a new instance
     *
     * @param k length
     * @return GunNettyBitMap
     */
    static GunNettyBitMap getInstance(int k) {
        return new GunNettyBitMapImpl(k);
    }

    /**
     * get i th value
     *
     * @param i i th
     * @return 0 or 1
     */
    boolean get(int i);

    /**
     * put i th value
     * @param i i th
     * @param tf 0 or 1
     */
    void put(int i, boolean tf);

    /**
     * put area
     * @param s s start area
     * @param tf true or false 0:1
     */
    default void putArea(int s, boolean... tf) {
        for (int j = s; j < tf.length; j++) {
            put(j, tf[j - s]);
        }
    }
}


class GunNettyBitMapImpl implements GunNettyBitMap {
    private long[] c;

    GunNettyBitMapImpl(int len) {
        c = new long[len / 64 + 1];
    }

    @Override
    public boolean get(int i) {
        return (c[(i / 64)] & (1 << (i % 64))) != 0;
    }

    @Override
    public void put(int i, boolean tf) {
        c[(i / 64)] = tf ? (c[(i / 64)] | (1 << (i % 64))) : (c[(i / 64)] & ~(1 << (i % 64)));
    }
}
