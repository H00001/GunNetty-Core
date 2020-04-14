/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.filter;

import top.gunplan.netty.impl.channel.GunNettyChannelException;
import top.gunplan.netty.impl.checker.GunInboundChecker;

/**
 * GunNettyDataFilter
 *
 * @author frank albert
 * @version 0.0.0.1
 */
public interface GunNettyDataFilter extends GunNettyFilter {

    /**
     * doInputFilter
     *
     * @param filterDto input filter dto
     * @return deal result {@link DealResult};
     * @throws GunNettyChannelException kinds of exception
     */
    DealResult doInputFilter(GunInboundChecker filterDto) throws GunNettyChannelException;
}
