/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.observe;

import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelObserve
 *
 * @author frank albert
 */
public interface GunNettyChannelObserve extends GunNettyObserve {

    /**
     * on recover read interest happened
     */
    void onRecoverReadInterest();


    /**
     * on close
     *
     * @param address remote address
     */
    void onClose(SocketAddress address);


    /**
     * when to close exception happened
     *
     * @param address   remote
     * @param throwable error
     */
    void whenCloseMeetException(SocketAddress address, Throwable throwable);


    /**
     * when register to i/o event loop
     *
     * @param channel channel
     */
    void whenRegister(SocketChannel channel);


    /**
     * when register exception happened
     *
     * @param channel   SocketChannel
     * @param throwable exception
     */
    void whenRegisterMeetException(SocketChannel channel, Throwable throwable);


}
