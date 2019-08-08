/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.Channel;

/**
 * GunNettyChannel
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-08 23:06
 */
public interface GunNettyChannel<CH extends Channel> extends Channel {

    /**
     * get the pipeline that the channel attach
     *
     * @return pipeline
     */
    GunNettyPipeline pipeline();


    /**
     * get java channel to transfer
     *
     * @return java channel
     */
    CH channel();


    GunNettyChannel setPipeline(GunNettyPipeline pipeline);


    long channelId();


    GunNettyChannel setChannelId(long id);


    SocketAddress remoteAddress() throws IOException;


}
