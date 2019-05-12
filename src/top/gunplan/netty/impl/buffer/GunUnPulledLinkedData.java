package top.gunplan.netty.impl.buffer;

import top.gunplan.netty.GunLinkedData;

/**
 * @author dosdrtt
 */
public class GunUnPulledLinkedData implements GunLinkedData {
    private final byte[] data;

    GunUnPulledLinkedData(byte[] data) {
        this.data = data;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public void release() {
        //do nothing
    }

    @Override
    public void addLink() {
        //do nothing
    }
}
