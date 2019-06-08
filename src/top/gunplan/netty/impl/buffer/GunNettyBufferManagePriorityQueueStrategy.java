package top.gunplan.netty.impl.buffer;

import java.lang.ref.SoftReference;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * GunNettyBufferManagePriorityQueueStrategy
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 16:10
 */
public class GunNettyBufferManagePriorityQueueStrategy implements GunNettyBufferManageStrategy {

    @Override
    public void onRelease(GunNettyBufferStream stream, Queue<SoftReference<GunNettyBufferStream>> operator, Queue<GunNettyBufferStream> using) {

    }

    @Override
    public GunNettyBufferStream onNeed(Queue<SoftReference<GunNettyBufferStream>> operator, Queue<GunNettyBufferStream> using, int size) {
        return null;
    }

    @Override
    public Queue<SoftReference<GunNettyBufferStream>> acquireSoftQueue() {
        return new PriorityBlockingQueue<>();
    }

    @Override
    public Queue<GunNettyBufferStream> acquireStrongQueue() {
        return new PriorityBlockingQueue<>();
    }
}
