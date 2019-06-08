package top.gunplan.netty.impl.buffer;

/**
 * GunBufferObserve
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 15:17
 */
public interface GunBufferObserve {
    void onRelease(GunNettyBufferStream stream);
}
