/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.impl.GunNettyChecker;

/**
 * GunOutboundChecker
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-13 17:21
 */
public interface GunOutboundChecker extends GunNettyChecker {
    boolean isHasDataToOutput();

}
