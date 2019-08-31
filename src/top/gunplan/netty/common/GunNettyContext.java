/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.common;


import top.gunplan.utils.GunLogger;
import top.gunplan.utils.GunNxLogger;

/**
 * GunNettyContext
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-27 08:09
 */

public final class GunNettyContext {
    public static GunLogger logger = new GunNxLogger();

    public static class NumberConst {
        public static final int ONE = 1;
        public static final int TWO = 2;
        public static final int THREE = 1;
    }

    public static class ICE {
        public static long nextValue() {
            return System.currentTimeMillis() << 10 & System.nanoTime() >>> 3;
        }
    }

}
