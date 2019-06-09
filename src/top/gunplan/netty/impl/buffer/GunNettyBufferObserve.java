package top.gunplan.netty.impl.buffer;

/**
 * GunNettyBufferObserve
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 15:17
 */
public interface GunNettyBufferObserve {
    /**
     * on release execute
     *
     * @param stream GunNettyBufferStream
     */
    void onRelease(GunNettyBufferStream stream);
}
