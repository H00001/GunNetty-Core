/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.impl.eventloop.GunConnEventLoop;

/**
 * SystemChannelChangedHandle
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-13 09:28
 */
public interface SystemChannelChangedHandle extends GunHandle {
    /**
     * whenBind
     *
     * @param port need to port
     */
    default void whenBind(int port) {
        GunNettyContext.logger.setTAG(SystemChannelChangedHandle.class).
                info("server running on :" + port);
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
}
