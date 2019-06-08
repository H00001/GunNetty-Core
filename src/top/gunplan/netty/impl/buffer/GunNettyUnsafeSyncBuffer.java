package top.gunplan.netty.impl.buffer;

import sun.misc.Unsafe;

public class GunNettyUnsafeSyncBuffer extends GunNettyUnsafeBuffer {
    public GunNettyUnsafeSyncBuffer(long memoryAddress, int len, Unsafe unsafe) {
        super(memoryAddress, len, unsafe);
    }


    /**
     * in fact if the type is int,
     * we do not need to write synchronized
     * keyword ,but it is long type ,you'd
     * know the long type was writen in 2
     * step from the register to memory :
     * first write 32 high bit the next is
     * write low 32 bits
     *
     * @return long write point
     */

    @Override
    public synchronized long writePoint() {
        return super.writePoint();
    }

    /**
     * in fact if the type is int,
     * we do not need to write synchronized
     * keyword ,but it is long type ,you'd
     * know the long type was writen in 2
     * step from the register to memory :
     * first write 32 high bit the next is
     * write low 32 bits
     *
     * @return long read point
     */
    @Override
    public synchronized long readPoint() {
        return super.readPoint();
    }

    @Override
    public synchronized void write(byte bin) {
        super.write(bin);
    }

    @Override
    public synchronized void write(byte[] bin) {
        super.write(bin);
    }

    @Override
    public synchronized byte[] read(int len) {
        return super.read(len);
    }

    @Override
    public synchronized byte read() {
        return super.read();
    }

}
