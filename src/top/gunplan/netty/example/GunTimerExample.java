/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.example;

import top.gunplan.netty.anno.GunTimeExecutor;
import top.gunplan.netty.impl.GunNettyChildTimer;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class GunTimerExample implements GunNettyChildTimer {
    public volatile int k = 0;


    @Override
    public int runingTimes() {
        return 0;
    }

    @GunTimeExecutor(interval = 10)
    @Override
    public int doWork(GunNettyChildChannel<SocketChannel> keys) {
        try {
            keys.channel().write(ByteBuffer.wrap(("hello old iron " +
                    "\nplease double click 666\n").getBytes()));
        } catch (IOException e) {
            keys.closeAndRemove(true);
        }
        if (k++ == 60) {
            keys.closeAndRemove(true);
            System.out.println("time out closed");
        }
        System.out.println(keys);
        System.out.println(k);
        return 0;
    }
}
