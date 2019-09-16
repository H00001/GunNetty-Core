/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunCoreEventLoop;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.util.Set;

/**
 * GunDataEventLoop
 *
 * @author frank albert
 * @version 0.0.0.2
 */
public interface GunDataEventLoop<U extends Channel> extends GunCoreEventLoop {


    /**
     * availableSelectionKey
     *
     * @return keys
     * @throws IOException fast release happened I/O error
     */
    Set<SelectionKey> availableSelectionKey() throws IOException;


    /**
     * incrAndContinueLoop
     */

    void incrAndContinueLoop();


    /**
     * registerReadKey
     *
     * @param channel javaChannel chan read
     * @return selector
     * @throws IOException register fail
     */
    SelectionKey registerReadKey(U channel) throws IOException;


    /**
     * make sum of select decrease
     */
    void decreaseAndStop();


    /**
     * fast release
     *
     * @return release value of javaChannel
     * @throws IOException i/o
     */

    int fastLimit() throws IOException;


    /**
     * submit async event
     *
     * @param runnable runner
     */
    void submit(Runnable runnable);
}
