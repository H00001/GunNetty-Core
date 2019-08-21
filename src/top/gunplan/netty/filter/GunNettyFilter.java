/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.filter;


import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunHandle;
import top.gunplan.netty.impl.checker.GunOutboundChecker;

/**
 * filter is a type of handle
 *
 * @author dosdrtt
 */
public interface GunNettyFilter extends GunHandle {

    /**
     * doing filter when the response occur
     *
     * @param filterDto input to the filter's deal Object
     * @return DealResult result true:next false:break
     * @throws GunChannelException kinds of exception
     */
    DealResult doOutputFilter(GunOutboundChecker filterDto) throws GunChannelException;


    enum DealResult {
        /**
         * NATALINA           :do not deal input filter ,go for handle right away
         * CLOSED             :do not deal any filter or handle
         * NEXT               :nothing will happened
         * NOT_DEAL_OUTPUT    :exit export filter chain and handle but it wasn't close
         * NOT_DEAL_ALL_NEXT  :exit all filter chain and handle but it wasn't close
         */
        NATALINA, CLOSED, NEXT, NOT_DEAL_OUTPUT, NOT_DEAL_ALL_NEXT
    }
}
