/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.example;

import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.anno.GunHandleTag;
import top.gunplan.netty.anno.GunInjectSelf;
import top.gunplan.netty.anno.GunTimeExecutor;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * GunTimerExample
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-31 23:37
 */
public class GunTimerExample implements GunNettyTimer {
    public volatile int k = 0;

    @GunTimeExecutor(interval = 10, t = @GunHandleTag(id = 140000001, name = "GunTimerExample"))
    public int doWork(@GunInjectSelf GunNettyChildChannel<SocketChannel> keys) {
        try {
            keys.channel().write(ByteBuffer.wrap(("please double click 666 doTime:" + (10 - k) + "\n").getBytes()));
        } catch (IOException e) {
            keys.closeAndRemove(true);
        }
        if (k++ == 10) {
            keys.closeAndRemove(true);
            System.out.println("doTime out closed");
        }
        System.out.println(keys);
        System.out.println(k);
        return 0;
    }

    @Override
    public boolean timeExecuteError(String methodName, ReflectiveOperationException t) {
        System.out.println(methodName + " execute is error");
        //  t.printStackTrace();
        return false;
    }
}
