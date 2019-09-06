/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import top.gunplan.netty.*;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.example.GunNettyCharsetInboundChecker;
import top.gunplan.netty.example.GunNettyStringHandle;
import top.gunplan.netty.example.GunString;
import top.gunplan.netty.example.GunTimerExample;
import top.gunplan.netty.filter.GunNettyFilter;
import top.gunplan.netty.filter.GunNettyInboundFilter;
import top.gunplan.netty.impl.GunBootServerFactory;
import top.gunplan.netty.impl.GunNettyDefaultObserve;
import top.gunplan.netty.impl.GunNettyStdFirstFilter;
import top.gunplan.netty.impl.property.GunGetPropertyFromNet;

import java.io.IOException;
import java.nio.ByteBuffer;

public class TestBase {

    public void preTest() {

    }

    public static void main(String[] args) throws InterruptedException {
        new TestBase().using019();
    }

    @Test
    public void using019() throws InterruptedException {
        GunNettyDefaultObserve p = new GunNettyDefaultObserve();
        GunNettySystemServices.PROPERTY_MANAGER.setStrategy(new GunGetPropertyFromNet("https://p.gunplan.top/config.html"));
        GunBootServer server = GunBootServerFactory.newInstance();
        server.setExecutors(GunNettyExecutors.newFixedExecutorPool(10),
                GunNettyExecutors.newFixedExecutorPool(10));
        server.registerObserve(new GunNettyDefaultObserve());
        server.registerObserve(p).onHasChannel(pipeline -> {
            pipeline.setMetaInfoChangeObserver(new DefaultGunNettyChildrenPipelineChangedObserve())
                    .addDataFilter(new GunNettyStdFirstFilter().setObserve(p))
                    .addDataFilter(new GunNettyCharsetInboundChecker())
                    .addConnFilter(new GunNettyStdFirstFilter())
                    .addDataFilter((GunNettyInboundFilter) filterDto -> {
                        if (((GunString) (filterDto.transferTarget())).get().startsWith("666")) {
                            ((GunTimerExample) filterDto.channel().timers().get(0)).k = 0;
                        } else {
                            try {
                                filterDto.channel().channel().write(ByteBuffer.wrap(("you are dead\nhia hia hia").getBytes()));
                                filterDto.channel().closeAndRemove(true);
                            } catch (IOException ignored) {
                            }
                            return GunNettyFilter.DealResult.CLOSED;
                        }
                        return GunNettyFilter.DealResult.NEXT;
                    })
                    .addNettyTimer(new GunTimerExample());
            pipeline.setHandle((GunNettyChildrenHandle) new GunNettyStringHandle());
            pipeline.setHandle((GunNettyParentHandle) new GunNettyStringHandle());
        });
        server.setSyncType(false);
        Assertions.assertEquals(server.sync(), GunBootServer.GunNettyWorkState.ASYNC.state |
                GunBootServer.GunNettyWorkState.RUNNING.state);
        //running time
        Thread.sleep(10000);
        System.out.println(GunBootServer.GunNettyWorkState.getState(server.stop()));
    }
}


