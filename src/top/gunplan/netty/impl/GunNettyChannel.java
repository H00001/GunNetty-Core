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
     * registerEventLoop
     *
     * @param eventLoop loop
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


    /**
     * Tells whether or not this key is valid.
     *
     * <p> A key is valid upon creation and remains so until it is cancelled,
     * its channel is closed, or its selector is closed.  </p>
     *
     * @return {@code true} if, and only if, this key is valid
     */
    boolean isValid();





}
