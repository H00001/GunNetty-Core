/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.example;

import top.gunplan.netty.anno.GunHandleTag;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.filter.GunNettyInboundFilter;
import top.gunplan.netty.impl.channel.GunNettyChannelException;
import top.gunplan.netty.impl.checker.GunInboundChecker;

/**
 * GunNettyCharsetInboundChecker
 * an example
 *
 * @author frank albert
 * @version 0.0.0.3
 * # 2019-07-26 23:21
 */
@GunNetFilterOrder(index = 1)
@GunHandleTag(name = "GunNettyCharsetInboundChecker", id = GunNettyContext.NumberConst.ONE)
public class GunNettyCharsetInboundChecker implements GunNettyInboundFilter {

    @Override
    public DealResult doInputFilter(GunInboundChecker filterDto) throws GunNettyChannelException {
        if (filterDto.tranToObject(GunString.class)) {
            return DealResult.NEXT;
        } else {
            return DealResult.NOT_DEAL_OUTPUT;
        }
    }
}
