/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunNettyChildrenHandle;
import top.gunplan.netty.GunNettyParentHandle;
import top.gunplan.netty.GunNettySystemServices;
import top.gunplan.netty.example.*;
import top.gunplan.netty.filter.GunNettyFilter;
import top.gunplan.netty.filter.GunNettyInboundFilter;
import top.gunplan.netty.impl.GunBootServerFactory;
import top.gunplan.netty.impl.GunNettyDefaultObserve;
import top.gunplan.netty.impl.GunNettyStdFirstFilter;
import top.gunplan.netty.impl.property.GunGetPropertyFromNet;
import top.gunplan.netty.observe.DefaultGunNettyChildrenPipelineChangedObserve;

import java.io.IOException;
import java.nio.ByteBuffer;

public class TestBase {

    @Test
    public void using019() throws InterruptedException {
        GunNettyDefaultObserve p = new GunNettyDefaultObserve();
        GunNettySystemServices.PROPERTY_MANAGER.setStrategy(new GunGetPropertyFromNet("https://p.gunplan.top/config.html"));
        GunBootServer server = GunBootServerFactory.newInstance();
        server.setExecutors(10, 10);
        server.registerObserve(new GunNettyDefaultObserve());
        server.registerObserve(p).onHasChannel(pipeline -> {
            pipeline.setMetaInfoChangeObserver(new DefaultGunNettyChildrenPipelineChangedObserve())
                    .addDataFilter(new GunNettyStdFirstFilter().setObserve(p))
                    .addDataFilter(new GunNettyCharsetInboundChecker())
                    .addConnFilter(new GunNettyStdFirstFilter())
                    .addDataFilter(getGunNettyInboundFilter())
                    .addNettyTimer(new GunTimerExample());
            pipeline.setHandle((GunNettyChildrenHandle) new GunNettyStringHandle());
            pipeline.setHandle((GunNettyParentHandle) new GunNettyStringHandle());
        });
        server.timeManager().addGlobalTimers(new GlobalTimer());
        server.setSyncType(false);
        Assertions.assertEquals(server.sync(), GunBootServer.GunNettyWorkState.ASYNC.state |
                GunBootServer.GunNettyWorkState.RUNNING.state);
        //running doTime
        Thread.sleep(1000000);
        System.out.println(GunBootServer.GunNettyWorkState.getState(server.stop()));
    }

    private GunNettyInboundFilter getGunNettyInboundFilter() {
        return filterDto -> {
            if (((GunString) (filterDto.transferTarget())).get().startsWith("666")) {
                filterDto.channel().pushEvent(1);
            } else {
                try {
                    filterDto.channel().channel().write(ByteBuffer.wrap(("you are dead\nhia hia hia").getBytes()));
                } catch (IOException ignored) {

                }
                filterDto.channel().closeAndRemove(true);

                return GunNettyFilter.DealResult.CLOSED;
            }
            return GunNettyFilter.DealResult.NEXT;
        };
    }
}


