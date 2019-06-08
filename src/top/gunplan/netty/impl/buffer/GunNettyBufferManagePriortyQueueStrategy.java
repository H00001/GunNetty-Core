package top.gunplan.netty.impl.buffer;

import java.lang.ref.SoftReference;
import java.util.Queue;

/**
 * GunNettyBufferManagePriortyQueueStrategy
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 16:10
 */
public class GunNettyBufferManagePriortyQueueStrategy implements GunNettyBufferManageStrategy {
    @Override
    public void onRelease(GunNettyBufferStream stream, Queue<SoftReference<GunNettyBufferStream>> operator, Queue<GunNettyBufferStream> using) {

    }

    @Override
    public GunNettyBufferStream onNeed(Queue<SoftReference<GunNettyBufferStream>> operator, Queue<GunNettyBufferStream> using, int size) {
        return null;
    }
}
