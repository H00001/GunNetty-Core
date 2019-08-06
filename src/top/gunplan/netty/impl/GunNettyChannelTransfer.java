/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty.impl;

import java.nio.channels.Channel;

/**
 * GunNettyChannelTransfer
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-07-17 07:39
 */
public interface GunNettyChannelTransfer<U extends Channel> {
    /**
     * transferTarget's channel
     *
     * @return channel
     */
    U channel();

    /**
     * set channel
     *
     * @param channel set channel
     */
    void setChannel(U channel);

    /**
     * set key
     *
     * @param key unique identifier
     */
    void setKey(long key);

    /**
     * get the key
     *
     * @return key unique identifier
     */
    long key();

}
