package top.gunplan.netty.impl.buffer;

import top.gunplan.netty.GunBufferManage;

import java.lang.ref.SoftReference;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * BaseBufferManager
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 15:37
 */
public abstract class BaseBufferManager implements GunBufferManage, GunBufferObserve {
    private Queue<GunNettyBufferStream> using = new ConcurrentLinkedDeque<>();
    private Queue<SoftReference<GunNettyBufferStream>> operatored = new ConcurrentLinkedDeque<>();
    private GunNettyBufferManageStrategy strategy;


    public BaseBufferManager(boolean sstrategy) {
        strategy = sstrategy ? new GunNettyBufferManageConcurrentStrategy() : new GunNettyBufferManagePriortyQueueStrategy();
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
            s = getBuffer(size);
            using.offer(s);
        }
        return s;
    }
}