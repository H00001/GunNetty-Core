/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.checker;

import top.gunplan.netty.impl.GunNettyChecker;
import top.gunplan.netty.impl.GunNettyTranslator;
import top.gunplan.netty.protocol.GunNetInbound;

/**
 * GunInboundChecker
 *
 * @author frank albert
 * @version 0.0.0.2
 */
public interface GunInboundChecker extends GunNettyChecker<GunNetInbound>, GunNettyTranslator {
}
