package top.gunplan.netty;


import top.gunplan.netty.impl.buffer.GunNettyBufferStream;

/**
 * @author dosdrtt
 */
public interface GunBufferManage {

    /**
     * get buffer
     * @param size size
     * @return GunNettyBufferStream
     */
    GunNettyBufferStream getBuffer(int size);

    /**
     * usingCount
     *
     * @return int buffer count
     */
    int usingCount();
}
