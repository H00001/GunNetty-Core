/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.buffer;


/**
 * GunNettySafeBufferManager
 *
 * @author frank albert
 * @version 0.0.0.1
 * # 2019-06-08 19:11
 */
class GunNettySafeBufferManager extends BaseNettyBufferManager {


    GunNettySafeBufferManager(BufferPoolStrategy s) {
        super(s);
    }


    @Override
    GunNettyBufferStream realGetBuffer(int size) {
        return new GunNettySafeBuffer(size);
    }

}
