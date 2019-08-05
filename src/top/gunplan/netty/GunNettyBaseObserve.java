/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty;

import top.gunplan.netty.common.GunNettyContext;

import java.net.SocketAddress;

/**
 * GunNettyBaseObserve
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-05 00:19
 */
public interface GunNettyBaseObserve {
    /**
     * onListen happened
     *
     * @param port port
     */
    default void onListen(int port) {
        GunNettyContext.logger.info("server running on :" + port);
    }

    default int preReadClose(SocketAddress address) {
        output(address);
        return 0;
    }

    default void output(SocketAddress address) {
        GunNettyContext.logger.debug("client closed:" + address.toString(), "[CONNECTION]");
    }

    default int preWriteClose(SocketAddress address) {
        output(address);
        return 0;
    }

}
