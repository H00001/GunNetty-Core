package top.gunplan.netty.impl.buffer;

import top.gunplan.netty.GunBufferManage;

/**
 * GunBufferFactory
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 14:56
 */
public final class GunBufferFactory {
    /**
     * SafeInstance
     * safe version
     */
    public static final class SafeInstance {
        private static final GunBufferManage HINDRANCE = new GunUnsafeBufferManage(true);

        public GunBufferManage getHinstance() {
            return HINDRANCE;
        }
    }

    public static final class UnSafeInstance {
        private static final GunBufferManage HINDRANCE = new GunUnsafeBufferManage(true);

        public GunBufferManage getHinstance() {
            return HINDRANCE;
        }
    }


}
