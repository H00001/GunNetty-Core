/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

/**
 * this is a handle interface ,include {@link GunNettyFilter }
 * and {@link GunNettyHandle}
 *
 * @author dosdrtt
 * @since 0.0.0.4
 */
public interface GunHandle {
    /**
     * init init the handle
     *
     * @return int init result 0:succeed
     * 1:error
     */
    default int init() {
        return 0;
    }


    /**
     * destroy
     * this function will be called at destroy
     *
     * @return int destroy result
     */

    default int destroy() {
        return 0;
    }


}
