/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;


import top.gunplan.netty.impl.GunInboundChecker;
import top.gunplan.netty.impl.channel.GunNettyChannel;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;

import java.nio.channels.SocketChannel;


/**
 * filter is a type of handle
 *
 * @author dosdrtt
 */
public interface GunNettyFilter extends GunHandle {
    /**
     * doInputFilter
     *
     * @param filterDto input filter dto
     * @return deal result {@link DealResult};
     * @throws GunChannelException kinds of exception
     */
    DealResult doInputFilter(GunInboundChecker filterDto) throws GunChannelException;

    /**
     * doing filter when the response occur
     *
     * @param filterDto input to the filter's deal Object
     * @return DealResult result true:next false:break
     * @throws GunChannelException kinds of exception
     */
    DealResult doOutputFilter(GunOutboundChecker filterDto) throws GunChannelException;

    /**
     * doOutputFilter
     *
     * @param filterDto filter dto
     * @param channel   channel to transferTarget
     * @return deal result
     */
    default DealResult doOutputFilter(GunOutboundChecker filterDto, GunNettyChannel<SocketChannel, GunDataEventLoop> channel) {
        return DealResult.NEXT;
    }


    /**
     * doConnFilter
     *
     * @param ch channel
     * @return is or not continue
     * @apiNote 0.1.2.3
     */
    default boolean doConnFilter(GunNettyChannel ch) {
        return true;
    }


    enum DealResult {
        /**
         * NATALINA           :do not deal input filter ,go for handle right away
         * CLOSE              :do not deal any filter or handle
         * NEXT               :nothing will happened
         * NOT_DEAL_OUTPUT    :exit export filter chain and handle but it wasn't close
         * NOT_DEAL_ALL_NEXT  :exit all filter chain and handle but it wasn't close
         * CLOSED_WHEN_READ:  :
         */
        NATALINA, CLOSE, NEXT, NOT_DEAL_OUTPUT, NOT_DEAL_ALL_NEXT, CLOSED_WHEN_READ
    }
}
