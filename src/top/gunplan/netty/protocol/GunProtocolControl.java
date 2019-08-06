/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.protocol;


/**
 * @author dosdrtt
 * @apiNote 0.0.0.1
 * @since 0.0.0.2
 */
@FunctionalInterface
public interface GunProtocolControl<I, O> {
    /**
     * get is or not return
     *
     * @return is or not return to client
     */
    default boolean isReturn() {
        return true;
    }


    O supply(I i);
}
