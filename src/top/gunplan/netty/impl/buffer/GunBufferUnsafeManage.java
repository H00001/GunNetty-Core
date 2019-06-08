package top.gunplan.netty.impl.buffer;

import top.gunplan.netty.GunBufferManage;

/**
 * GunBufferUnsafeManage
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-08 14:04
 */
public interface GunBufferUnsafeManage extends GunBufferManage {

    /**
     * malloc
     *
     * @param size
     * @param len
     * @return
     */
    long malloc(int size, int len);


    /**
     * malloc
     *
     * @param len
     * @return
     */

    long malloc(int len);
}
