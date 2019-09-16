/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.ChannelInitHandle;
import top.gunplan.netty.SystemChannelChangedHandle;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * ConnEventLoopBuilder
 *
 * @author frank albert
 * @version 0.0.0.1
 */
public interface ConnEventLoopBuilder extends EventLoopBuilder<GunConnEventLoop> {
    /**
     * bindPort
     *
     * @param p port
     * @return this chain style
     * @throws IOException when bind file
     */
    ConnEventLoopBuilder bindPort(int p) throws IOException;


    /**
     * with the parameter to init
     *
     * @param deal           executor
     * @param parentHandle   parentHandle
     * @param childrenHandle childrenHandle
     * @return this
     * @throws IOException create fail
     */
    ConnEventLoopBuilder with(ExecutorService deal, SystemChannelChangedHandle parentHandle, ChannelInitHandle childrenHandle) throws IOException;
}
