/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.filter;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.impl.checker.GunOutboundChecker;

/**
 * GunNettyInboundFilter
 *
 * @author frank albert
 * @version 0.0.0.1
 */
@FunctionalInterface
public interface GunNettyInboundFilter extends GunNettyDataFilter {

    /**
     * doOutputFilter
     *
     * @param filterDto input to the filter's deal Object
     * @return Deal result {@link GunNettyFilter.DealResult}
     * @throws GunChannelException channel error
     */
    @Override
    default DealResult doOutputFilter(GunOutboundChecker filterDto) throws GunChannelException {
        return DealResult.NEXT;
    }
}
