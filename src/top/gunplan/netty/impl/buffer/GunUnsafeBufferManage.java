package top.gunplan.netty.impl.buffer;

import sun.misc.Unsafe;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.lang.reflect.Field;


/**
 * GunUnsafeBufferManage
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 15:05
 */
public class GunUnsafeBufferManage extends BaseBufferManager implements GunBufferObserve {
    private static final String TOKEN = "theUnsafe";
    private static Unsafe unsafe = null;

    GunUnsafeBufferManage(boolean s) {
        super(s);
        try {
            Field getUnsafe = Unsafe.class.getDeclaredField(TOKEN);
            getUnsafe.setAccessible(true);
            unsafe = (Unsafe) getUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            AbstractGunBaseLogUtil.error(e);
        }
    }

    private long malloc(int size, int len) {
        return malloc(size * len);
    }

    private long malloc(int len) {
        return unsafe.allocateMemory(len);
    }


    @Override
    public GunNettyBufferStream getBuffer(int size) {
        long address = malloc(size);
        return new GunNettyUnsafeBuffer(address, size, unsafe)
                .registerObs(this);
    }
}
