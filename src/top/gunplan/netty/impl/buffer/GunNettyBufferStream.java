package top.gunplan.netty.impl.buffer;

import java.util.Comparator;

/**
 * GunNettyBufferStream
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 14:12
 */
public interface GunNettyBufferStream extends Comparable<GunNettyBufferStream> {

    /**
     * maxLen
     *
     * @return max index length
     */
    long maxLen();

    /**
     * readPoint
     *
     * @return point index position
     */
    long readPoint();


    /**
     * writePoint
     *
     * @return point index position
     */
    long writePoint();


    /**
     * flush point
     * make read,write point convert 0
     */
    void flushPoint();


    /**
     * move read position
     *
     * @param position read position
     */
    void setReadPosition(long position);

    /**
     * move write position
     *
     * @param position write position
     */
    void setWritePosition(long position);


    /**
     * write
     * write list of byte
     *
     * @param bin bytes to write
     */
    void write(byte[] bin);

    /**
     * write
     * write a byte
     *
     * @param bin byte will be write
     */
    void write(byte bin);


    /**
     * read
     * read a byte
     *
     * @return read result
     */
    byte read();

    /**
     * read
     *
     * @param len length of bytes will be read
     * @return read result
     */
    byte[] read(int len);


    /**
     * release use
     */
    void release();


    /**
     * registerObs
     * <p>
     * register objects
     *
     * @param observe GunNettyBufferObserve
     * @return return this chain style
     */
    GunNettyBufferStream registerObs(GunNettyBufferObserve observe);

    /**
     * remove data
     */
    void flushData();


    /**
     * get buffer status
     *
     * @return true: using
     * false : not using
     */
    boolean getStatus();

    /**
     * set status
     */

    void setUsed();


    @Override
    default int compareTo(GunNettyBufferStream o) {
        return Long.compare(this.maxLen(), o.maxLen());
    }
}
