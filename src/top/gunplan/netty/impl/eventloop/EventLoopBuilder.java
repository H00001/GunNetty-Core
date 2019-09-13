/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.impl.GunNettyEventLoopManager;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * EventLoopBuilder
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-23 00:10
 */
public interface EventLoopBuilder<U> {
    /**
     * build
     *
     * @return a event loop builder:self
     */
    U build();

    /**
     * andRegister
     *
     * @param manager GunNettyCoreThreadManager
     * @return EventLoopBuilder<U>
     */
    EventLoopBuilder<U> andRegister(GunNettyEventLoopManager manager);

    /**
     * with
     * register executor
     *
     * @param deal ExecutorService
     * @return this chain style
     * @throws IOException i/o error
     */
    EventLoopBuilder<U> with(ExecutorService deal) throws IOException;

}
