/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
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
 * @date 2019-07-22 23:29
 */
public interface GunDataEventLoop<U extends Channel> extends GunCoreEventLoop {


    /**
     * availableSelectionKey
     *
     * @return Set<SelectionKey>
     */
    Set<SelectionKey> availableSelectionKey();


    /**
     * incrAndContinueLoop
     */

    void incrAndContinueLoop();


    /**
     * registerReadKey
     *
     * @param channel channel chan read
     * @return selector
     * @throws IOException register fail
     */
    SelectionKey registerReadKey(U channel) throws IOException;


    /**
     * make sum of select decrease
     */
    void decreaseAndStop();
}
