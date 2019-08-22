/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.observe;

import top.gunplan.netty.common.GunNettyContext;

import java.net.SocketAddress;

/**
 * GunNettyBaseObserve
 *
 * @author frank albert
 * @version 0.0.0.y
 * @date 2019-08-05 00:19
 */
public interface GunNettyBaseObserve extends GunNettyObserve {
    /**
     * Ready-to-read termination
     *
     * @param address remote address
     * @return wait time
     */
    default int preReadClose(SocketAddress address) {
        output(address);
        return 0;
    }

    /**
     * output
     *
     * @param address remote address
     */
    private void output(SocketAddress address) {
        GunNettyContext.logger.debug("client closed:" + address.toString(), "[CONNECTION]");
    }

    /**
     * Ready-to-write termination
     *
     * @param address remote address
     * @return wait time
     */
    default int preWriteClose(SocketAddress address) {
        output(address);
        return 0;
    }


}
