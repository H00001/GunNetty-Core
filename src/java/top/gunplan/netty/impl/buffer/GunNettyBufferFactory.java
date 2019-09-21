/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.buffer;

/**
 * GunNettyBufferFactory
 *
 * @author frank albert
 * @version 0.0.0.1
 */
public final class GunNettyBufferFactory {
    /**
     * SafeConcurrentInstance
     * safe version
     */
    public static final class SafeConcurrentInstance {
        private static final GunBufferManage HINDRANCE = new GunNettySafeBufferManager(BaseNettyBufferManager.BufferPoolStrategy.ConcurrentStrategy);

        public static GunBufferManage getHinstance() {
            return HINDRANCE;
        }
    }

    public static final class SafeProiortyInstance {
        private static final GunBufferManage HINDRANCE = new GunNettySafeBufferManager(BaseNettyBufferManager.BufferPoolStrategy.PriorityQueueStrategy);

        public static GunBufferManage getHinstance() {
            return HINDRANCE;
        }
    }

    public static final class UnSafeConcurrentInstance {
        private static final GunBufferManage HINDRANCE = new GunNettyUnsafeBufferManage(BaseNettyBufferManager.BufferPoolStrategy.ConcurrentStrategy);

        public static GunBufferManage getHinstance() {
            return HINDRANCE;
        }
    }


    public static final class UnSafeProiortyInstance {
        private static final GunBufferManage HINDRANCE = new GunNettySafeBufferManager(BaseNettyBufferManager.BufferPoolStrategy.PriorityQueueStrategy);

        public GunBufferManage getHinstance() {
            return HINDRANCE;
        }
    }


}
