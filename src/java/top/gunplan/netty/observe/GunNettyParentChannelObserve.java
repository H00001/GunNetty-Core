/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.observe;

import java.net.SocketAddress;

/**
 * GunNettyParentChannelObserve
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-09-21 08:16
 */

public interface GunNettyParentChannelObserve extends GunNettyObserve {
    /**
     * on child channel connected execute
     *
     * @param address connect address
     */
    void onChildrenChannelConnected(SocketAddress address);
}
