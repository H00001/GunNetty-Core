/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty;


import top.gunplan.netty.impl.GunNettyInputFilterChecker;
import top.gunplan.netty.impl.GunNettyOutputFilterChecker;

import java.nio.channels.Channel;
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
    DealResult doInputFilter(GunNettyInputFilterChecker filterDto) throws GunChannelException;

    /**
     * doing filter when the response occur
     *
     * @param filterDto input to the filter's deal Object
     * @return DealResult result true:next false:break
     * @throws GunChannelException kinds of exception
     */
    DealResult doOutputFilter(GunNettyOutputFilterChecker filterDto) throws GunChannelException;

    /**
     * doOutputFilter
     *
     * @param filterDto filter dto
     * @param channel   channel to transfer
     * @return deal result
     */
    default DealResult doOutputFilter(GunNettyOutputFilterChecker filterDto, SocketChannel channel) {
        return DealResult.NEXT;
    }


    /**
     * doConnFilter
     *
     * @param ch channel
     * @return is or not continue
     * @apiNote 0.1.2.3
     */
    default boolean doConnFilter(Channel ch) {
        return true;
    }


    enum DealResult {
        /**
         * NATALINA      :do not deal input filter ,go for handle right away
         * CLOSE         :do not deal any filter or handle
         * NEXT          :nothing will happened
         * NOTDEALOUTPUT :exit output filter chain and handle but it wasn't close
         * NOTDEALALLNEXT:ecit all filter chain and handle but it wasn't close
         */
        NATALINA, CLOSE, NEXT, NOTDEALOUTPUT, NOTDEALALLNEXT
    }
}
