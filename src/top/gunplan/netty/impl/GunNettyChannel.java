/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.GunCoreEventLoop;

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
public interface GunNettyChannel<CH extends Channel, LOOP extends GunCoreEventLoop> extends Channel {

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


    /**
     * set pipeline
     * @param pipeline pipeline
     * @return this:self
     */
    //GunNettyChannel setPipeline(GunNettyPipeline pipeline);


    /**
     * get channel id
     *
     * @return cid
     */
    long channelId();


    /**
     * set channel id
     *
     * @param id cid
     * @return self
     */

    GunNettyChannel<CH, LOOP> registerEventLoop(LOOP eventLoop);

    /**
     * get remote address
     *
     * @return SocketAddress
     * @throws IOException i/o error
     */
    SocketAddress remoteAddress() throws IOException;

    /**
     * get local address
     *
     * @return SocketAddress
     * @throws IOException i/o error
     */
    SocketAddress localAddress() throws IOException;

}
