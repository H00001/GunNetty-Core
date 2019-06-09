package top.gunplan.netty.impl.buffer;

import top.gunplan.netty.GunBufferManage;

import java.lang.ref.SoftReference;
import java.util.Queue;

/**
 * BaseNettyBufferManager
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 15:37
 */
public abstract class BaseNettyBufferManager implements GunBufferManage, GunNettyBufferObserve {
    private Queue<GunNettyBufferStream> using;
    private Queue<SoftReference<GunNettyBufferStream>> operatored;
    private GunNettyBufferManageStrategy strategy;

    BaseNettyBufferManager(BufferPoolStrategy s) {
        strategy = s.getStrategy();
        operatored = strategy.acquireSoftQueue();
        using = strategy.acquireStrongQueue();
    }


    public enum BufferPoolStrategy {
        /**
         * PriorityQueueStrategy
         */
        PriorityQueueStrategy(new GunNettyBufferManagePriorityQueueStrategy()), ConcurrentStrategy(new GunNettyBufferManageConcurrentStrategy());
        private GunNettyBufferManageStrategy strategy;

        BufferPoolStrategy(GunNettyBufferManageStrategy strategy) {
            this.strategy = strategy;
        }

        public GunNettyBufferManageStrategy getStrategy() {
            return strategy;
        }
    }

    @Override
    public void onRelease(GunNettyBufferStream stream) {
        strategy.onRelease(stream, operatored, using);
    }

    /**
     * getBuffer
     *
     * @param size size
     * @return GunNettyBufferStream
     */
    @Override
    public GunNettyBufferStream getBuffer(int size) {
        GunNettyBufferStream s = strategy.onNeed(operatored, using, size);
        if (strategy == null) {
            s = realGetBuffer(size);
            using.offer(s);
        }
        return s;
    }

    /**
     * real create buffer method
     *
     * @param size size
     * @return GunNettyBufferStream
     */
    abstract GunNettyBufferStream realGetBuffer(int size);

    @Override
    public int usingCount() {
        return using.size();
    }
}