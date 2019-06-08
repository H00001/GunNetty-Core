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
    public static final class safeInstance {
        private static final GunBufferManage HINSTANCE = new GunUnsafeBufferManage();

        public GunBufferManage getHinstance() {
            return HINSTANCE;
        }
    }

    public static final class unSafeInstance {
        private static final GunBufferManage HINSTAANCE = new GunUnsafeBufferManage();

        public GunBufferManage getHinstance() {
            return HINSTAANCE;
        }
    }


}
