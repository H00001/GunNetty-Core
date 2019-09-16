/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.buffer;

import java.lang.ref.SoftReference;
import java.util.Queue;

/**
 * GunNettyBufferManageStrategy
 *
 * @author frank albert
 * @version 0.0.0.3
 * # 2019-06-08 15:58
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
     * @param size     size of need
     * @return GunNettyBufferStream create a buffer
     */
    GunNettyBufferStream onNeed(Queue<SoftReference<GunNettyBufferStream>> operator,
                                Queue<GunNettyBufferStream> using, int size);


    /**
     * get unused queue
     *
     * @return queue
     */
    Queue<SoftReference<GunNettyBufferStream>> acquireSoftQueue();


    /**
     * get used queue
     *
     * @return queue in using
     */
    Queue<GunNettyBufferStream> acquireStrongQueue();
}
