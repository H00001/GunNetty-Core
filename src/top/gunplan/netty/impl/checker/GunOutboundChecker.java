/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.checker;

import top.gunplan.netty.impl.GunNettyChecker;
import top.gunplan.netty.protocol.GunNetOutbound;

/**
 * GunOutboundChecker
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-13 17:21
 */
public interface GunOutboundChecker extends GunNettyChecker<GunNetOutbound> {
    /**
     * there have data need to transfer
     *
     * @return is or not have
     */
    boolean isHasDataToOutput();

}
