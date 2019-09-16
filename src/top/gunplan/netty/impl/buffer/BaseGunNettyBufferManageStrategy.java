package top.gunplan.netty.impl.buffer;

import java.lang.ref.SoftReference;
import java.util.Queue;

/**
 * BaseGunNettyBufferManageStrategy
 *
 * @author frank albert
 * @version 0.0.0.1
 * # 2019-06-09 18:36
 */
public abstract class BaseGunNettyBufferManageStrategy implements GunNettyBufferManageStrategy {
    @Override
    public void onRelease(GunNettyBufferStream stream, Queue<SoftReference<GunNettyBufferStream>> operator, Queue<GunNettyBufferStream> using) {
        stream.flushPoint();
        stream.flushData();
        using.remove(stream);
    }
}
