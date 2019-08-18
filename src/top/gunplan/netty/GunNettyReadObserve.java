/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

/**
 * GunNettyReadObserve
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-18 22:36
 */
public interface GunNettyReadObserve {

    /**
     * on recover read interest happened
     */
    void onRecoverReadInterest();


    void onClose(SocketAddress address);


    void whenCloseMeetException(SocketAddress address, Throwable throwable);


    void whenRegister(SocketChannel channel);


}
