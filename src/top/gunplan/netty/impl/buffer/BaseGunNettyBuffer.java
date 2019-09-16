package top.gunplan.netty.impl.buffer;

import top.gunplan.netty.GunException;

import static top.gunplan.netty.GunExceptionType.OUT_POSITION;

/**
 * BaseGunNettyBuffer
 *
 * @author frank albert
 * @version 0.0.0.1
 * # 2019-06-08 14:15
 */
public abstract class BaseGunNettyBuffer implements GunNettyBufferStream {
    final long maxLen;
    long readPoint;
    long writePoint;
    private GunNettyBufferObserve observe;
    private boolean isLink;

    public BaseGunNettyBuffer(int len) {
        this.maxLen = len;
    }

    @Override
    public long maxLen() {
        return maxLen;
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
        if (postion > this.maxLen) {
            throw new GunException(OUT_POSITION, BaseGunNettyBuffer.class.getSimpleName());
        }
        this.readPoint = postion;
    }


    @Override
    public void flushPoint() {
        this.readPoint = 0;
        this.writePoint = 0;
    }

    @Override
    public void setWritePosition(long postion) {
        if (postion > this.maxLen) {
            throw new GunException(OUT_POSITION, BaseGunNettyBuffer.class.getSimpleName());
        }
        this.writePoint = postion;
    }


    @Override
    public void release() {
        isLink = false;
        this.observe.onRelease(this);
    }


    @Override
    public void setUsed() {
        this.isLink = true;
    }

    @Override
    public GunNettyBufferStream registerObs(GunNettyBufferObserve observe) {
        this.observe = observe;
        return this;
    }


    @Override
    public boolean getStatus() {
        return this.isLink;
    }
}
