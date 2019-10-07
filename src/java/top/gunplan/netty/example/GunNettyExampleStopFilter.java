/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.example;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.filter.GunNettyFilter;
import top.gunplan.netty.filter.GunNettyInboundFilter;
import top.gunplan.netty.impl.checker.GunInboundChecker;

import java.io.IOException;
import java.nio.ByteBuffer;

public class GunNettyExampleStopFilter implements GunNettyInboundFilter {


    @Override
    public DealResult doInputFilter(GunInboundChecker filterDto) throws GunChannelException {
        System.out.println(filterDto.channel().pipeline().toString());
        if (((GunString) (filterDto.transferTarget())).get().startsWith("666")) {
            filterDto.channel().pushEvent(1);
        } else {
            try {
                filterDto.channel().channel().write(ByteBuffer.wrap(("you are dead\nhia hia hia\n").getBytes()));
            } catch (IOException ignored) {
            }
            filterDto.channel().closeAndRemove(true);
            return GunNettyFilter.DealResult.CLOSED;
        }
        return GunNettyFilter.DealResult.NEXT;
    }
}
