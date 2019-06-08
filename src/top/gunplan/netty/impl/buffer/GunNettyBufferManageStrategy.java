package top.gunplan.netty.impl.buffer;

import java.lang.ref.SoftReference;
import java.util.Queue;

/**
 * GunNettyBufferManageStrategy
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 15:58
 */
public interface GunNettyBufferManageStrategy {
    /**
     * on Release
     *
     * @param stream   GunNettyBufferStream
     * @param operator can allocation queue
     * @param using    using queue
     */
    void onRelease(GunNettyBufferStream stream, Queue<SoftReference<GunNettyBufferStream>> operator,
                   Queue<GunNettyBufferStream> using);


    /**
     * on Need
     *
     * @param operator can allocation queue
     * @param using    using queue
     */
    GunNettyBufferStream onNeed(Queue<SoftReference<GunNettyBufferStream>> operator,
                                Queue<GunNettyBufferStream> using, int size);
}
