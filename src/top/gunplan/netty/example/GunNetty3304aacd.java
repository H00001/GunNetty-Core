/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.example;

import top.gunplan.netty.filter.GunNettyFilter;
import top.gunplan.netty.filter.GunNettyInboundFilter;

import java.io.IOException;
import java.nio.ByteBuffer;

public class GunNetty3304aacd {
    public static GunNettyInboundFilter getGunNettyInboundFilter() {
        return filterDto -> {
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
        };
    }
}
