package top.gunplan.netty.impl.buffer;

/**
 * GunNettyBufferStream
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 14:12
 */
public interface GunNettyBufferStream {

    /**
     * maxLen
     *
     * @return max length
     */
    long maxLen();

    /**
     * readPoint
     *
     * @return point postion
     */
    long readPoint();


    /**
     * writePoint
     *
     * @return point postion
     */
    long writePoint();


    /**
     * flush point
     */
    void flushPoint();


    /**
     * @param postion read postion
     */
    void setReadPosition(long postion);

    /**
     * @param postion write postion
     */
    void setWritePostion(long postion);


    void write(byte[] bin);


    void write(byte bin);


    byte read();

    byte[] read(int len);


    void release();


    GunNettyBufferStream registerObs(GunBufferObserve observe);

    void flushData();

}
