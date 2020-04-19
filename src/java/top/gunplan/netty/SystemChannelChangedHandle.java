/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.impl.eventloop.GunConnEventLoop;

import java.io.IOException;
import java.util.Arrays;

/**
 * SystemChannelChangedHandle
 *
 * @author frank albert
 * @version 0.0.0.2
 */
public interface SystemChannelChangedHandle extends GunHandle {
    /**
     * whenBind
     *
     * @param port need to port
     */
    default void whenBind(int... port) {
        GunNettyContext.logger.setTAG(SystemChannelChangedHandle.class).
                info("server running on :" + Arrays.toString(port));
    }


    /**
     * connEventLoopStop
     *
     * @param port event loop
     */
    default void connEventLoopStop(GunConnEventLoop port) {
        GunNettyContext.logger.setTAG(SystemChannelChangedHandle.class).
                info("even loop stop:" + port);
    }


    /**
     * failToCreateChildChannel call back
     *
     * @param throwable error
     */
    default void failToCreateChildChannel(Throwable throwable) {
        GunNettyContext.logger.setTAG(SystemChannelChangedHandle.class).
                info("failToCreateChildChannel:" + throwable);
    }

    /**
     * failToCreateParentChannel call back
     *
     * @param throwable i/o error
     */
    default void failToCreateParentChannel(IOException throwable) {
        GunNettyContext.logger.setTAG(SystemChannelChangedHandle.class).
                info("failToCreateParentChannel:" + throwable);
    }
}
