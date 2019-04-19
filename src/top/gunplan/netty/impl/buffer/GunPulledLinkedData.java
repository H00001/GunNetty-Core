package top.gunplan.netty.impl.buffer;

import top.gunplan.netty.GunBufferManage;
import top.gunplan.netty.GunLinkedData;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author dosdrtt
 */
public class GunPulledLinkedData implements GunLinkedData {
    private final byte[] data;
    private LongAdder times = new LongAdder();
    private GunBufferManage lenstener;

    GunPulledLinkedData(byte[] data) {
        this.data = data;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    long getTime() {
        return times.longValue();
    }

    private void incr() {
        times.increment();
        this.lenstener.addEvent();
    }

    private void desc() {
        times.decrement();
        this.lenstener.descEvent();
    }

    @Override
    public void addLink() {
        this.incr();
    }

    @Override
    public void release() {
        this.desc();
    }


    public void register(GunBufferManage listener) {
        this.lenstener = listener;
    }
}
