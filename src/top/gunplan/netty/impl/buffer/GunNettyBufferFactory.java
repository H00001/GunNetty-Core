package top.gunplan.netty.impl.buffer;

import top.gunplan.netty.GunBufferManage;

/**
 * GunNettyBufferFactory
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 14:56
 */
public final class GunNettyBufferFactory {
    /**
     * SafeConcurrentInstance
     * safe version
     */
    public static final class SafeConcurrentInstance {
        private static final GunBufferManage HINDRANCE = new GunNettySafeBufferManager(BaseNettyBufferManager.BufferPoolStrategy.ConcurrentStrategy);

        public GunBufferManage getHinstance() {
            return HINDRANCE;
        }
    }

    public static final class SafeProiortyInstance {
        private static final GunBufferManage HINDRANCE = new GunNettySafeBufferManager(BaseNettyBufferManager.BufferPoolStrategy.PriorityQueueStrategy);

        public GunBufferManage getHinstance() {
            return HINDRANCE;
        }
    }

    public static final class UnSafeConcurrentInstance {
        private static final GunBufferManage HINDRANCE = new GunNettyUnsafeBufferManage(BaseNettyBufferManager.BufferPoolStrategy.ConcurrentStrategy);

        public GunBufferManage getHinstance() {
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
