package top.gunplan.netty.impl.buffer;


/**
 * BaseGunNettyUnsafeBuffer
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 18:52
 */
public abstract class BaseGunNettyUnsafeBuffer extends BaseGunNettyBuffer {
    public BaseGunNettyUnsafeBuffer(int len) {
        super(len);
    }
    // final long memorySegmentAddress;
    //  final Unsafe unsafe;

//
//    BaseGunNettyUnsafeBuffer(long memoryAddress, int len, Unsafe unsafe) {
//        super(len);
//        this.memorySegmentAddress = memoryAddress;
//  //      this.unsafe = unsafe;
//    }

    /**
     * write
     *
     * @param bin bytes to write
     */
    @Override
    public abstract void write(byte[] bin);

    /**
     * write
     * @param bin byte will be write
     */
    @Override
    public abstract void write(byte bin);

    /**
     * read
     *
     * @return read result
     */
    @Override
    public abstract byte read();

    /**
     * read
     *
     * @param len length of bytes will be read
     * @return read result
     */
    @Override
    public abstract byte[] read(int len);

    /**
     * flushData
     */
    @Override
    public void flushData() {
        // unsafe.setMemory(memorySegmentAddress, maxLen, (byte) 0);
    }


}
