package top.gunplan.netty.impl.buffer;

import top.gunplan.netty.GunException;

import static top.gunplan.netty.GunExceptionTypes.OUT_POSITION;

/**
 * BaseGunNettyUnsafeBuffer
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 14:15
 */
public abstract class BaseGunNettyUnsafeBuffer implements GunNettyBufferStream {
    long maxlen;
    long readPoint;
    long writePoint;
    private GunBufferObserve observe;
    private boolean isLink;

    public BaseGunNettyUnsafeBuffer(int len) {
        this.maxlen = len;
    }

    @Override
    public long maxLen() {
        return maxlen;
    }

    @Override
    public long readPoint() {

        return readPoint;
    }

    @Override
    public long writePoint() {
        return writePoint;
    }

    @Override
    public void setReadPosition(long postion) {
        if (postion > this.maxlen) {
            throw new GunException(OUT_POSITION, BaseGunNettyUnsafeBuffer.class.getSimpleName());
        }
        this.readPoint = postion;
    }


    @Override
    public void flushPoint() {
        this.readPoint = 0;
        this.writePoint = 0;
    }

    @Override
    public void setWritePostion(long postion) {
        if (postion > this.maxlen) {
            throw new GunException(OUT_POSITION, BaseGunNettyUnsafeBuffer.class.getSimpleName());
        }
        this.writePoint = postion;
    }


    @Override
    public void release() {
        isLink = false;
        this.observe.onRelease(this);
    }

    @Override
    public GunNettyBufferStream registerObs(GunBufferObserve observe) {
        this.observe = observe;
        return this;
    }

}
