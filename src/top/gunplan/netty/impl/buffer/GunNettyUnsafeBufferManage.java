package top.gunplan.netty.impl.buffer;



/**
 * GunNettyUnsafeBufferManage
 *
 * @author frank albert
 * @version 0.0.0.1
 * # 2019-06-08 15:05
 */
public class GunNettyUnsafeBufferManage extends BaseNettyBufferManager implements GunNettyBufferObserve {
    private static final String TOKEN = "theUnsafe";

    GunNettyUnsafeBufferManage(BufferPoolStrategy s) {
        super(s);
//        try {
//            Field getUnsafe = Unsafe.class.getDeclaredField(TOKEN);
//            getUnsafe.setAccessible(true);
//          //  unsafe = (Unsafe) getUnsafe.get(null);
////        } catch (NoSuchFieldException | IllegalAccessException e) {
////            GunNettyContext.logger.error(e);
////        }
    }

    private long malloc(int size, int len) {
        return malloc(size * len);
    }

    private long malloc(int len) {
        return -1;
        //unsafe.allocateMemory(len);
    }


    @Override
    public GunNettyBufferStream realGetBuffer(int size) {
        long address = malloc(size);
        return null;
//        return new GunNettyUnsafeBuffer(address, size, unsafe)
//                .registerObs(this);
    }
}
