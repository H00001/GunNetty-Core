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
     * transfer's channel
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
