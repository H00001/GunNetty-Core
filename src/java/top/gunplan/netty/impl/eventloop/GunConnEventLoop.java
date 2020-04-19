/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunCoreEventLoop;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.concurrent.ExecutorService;

/**
 * GunConnEventLoop
 *
 * @author frank albert
 * @version 0.0.0.3
 */
public interface GunConnEventLoop extends GunCoreEventLoop {
    /**
     * open port
     * open port
     *
     * @param port port
     * @return succeed , normal
     * @throws IOException bind port error
     */
    int openPort(int... port) throws IOException;


    /**
     * listen port
     *
     * @return port
     */
    int[] listenPort();


    /**
     * get select
     *
     * @return select;
     */
    Selector select();


    /**
     * init with param
     *
     * @param service service
     * @return result
     * @throws IOException init error
     */
    @Override
    int init(ExecutorService service) throws IOException;
}
