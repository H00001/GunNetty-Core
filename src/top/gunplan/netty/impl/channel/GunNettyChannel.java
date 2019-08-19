/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.channel;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.impl.pipeline.GunNettyPipeline;

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
public interface GunNettyChannel<CH extends Channel,
        LOOP extends GunCoreEventLoop,
        PL extends GunNettyPipeline> extends Channel {

    /**
     * get the handle that the channel attachment
     *
     * @return handle
     */
    PL pipeline();


    /**
     * get java channel to transfer
     *
     * @return java channel
     */
    CH channel();


    /**
     * set handle
     * @param handle handle
     * @return this:self
     */
    //GunNettyChannel setPipeline(GunNettyPipeline handle);


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

    GunNettyChannel<CH, LOOP, PL> registerEventLoop(LOOP eventLoop);

    /**
     * get remote address
     *
     * @return SocketAddress
     */
    SocketAddress remoteAddress();

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
