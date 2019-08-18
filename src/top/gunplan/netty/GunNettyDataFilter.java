/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.impl.GunInboundChecker;

/**
 * GunNettyDataFilter
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-18 11:40
 */
public interface GunNettyDataFilter extends GunNettyFilter {

    /**
     * doInputFilter
     *
     * @param filterDto input filter dto
     * @return deal result {@link DealResult};
     * @throws GunChannelException kinds of exception
     */
    DealResult doInputFilter(GunInboundChecker filterDto) throws GunChannelException;
}
