/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.impl.GunInboundChecker;

/**
 * GunNettyOutboundFilter
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-27 08:09
 */

@FunctionalInterface
public interface GunNettyOutboundFilter extends GunNettyDataFilter {
    /**
     * doInputFilter
     *
     * @param filterDto input filter dto
     * @return DealResult
     * @throws GunChannelException channel i/o error
     */
    @Override
    default DealResult doInputFilter(GunInboundChecker filterDto) throws GunChannelException {
        return DealResult.NEXT;
    }
}
