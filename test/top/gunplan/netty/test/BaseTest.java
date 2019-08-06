/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.test;

import org.junit.jupiter.api.Test;
import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunNettySystemServices;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.example.GunNettyCharsetInboundChecker;
import top.gunplan.netty.example.GunNettyStringHandle;
import top.gunplan.netty.impl.GunBootServerFactory;
import top.gunplan.netty.impl.GunNettyDefaultObserve;
import top.gunplan.netty.impl.GunNettyStdFirstFilter;
import top.gunplan.netty.impl.property.GunGetPropertyFromNet;

public class BaseTest {

    public void preTest() {

    }

    @Test
    public void using019() throws InterruptedException {
        GunNettySystemServices.PROPERTY_MANAGER.setStrategy(new GunGetPropertyFromNet("https://p.gunplan.top/config1.html"));
        GunBootServer server = GunBootServerFactory.getInstance();
        server.setExecutors(GunNettyExecutors.newFixedExecutorPool(10),
                GunNettyExecutors.newFixedExecutorPool(10));
        server.registerObserve(new GunNettyDefaultObserve());
        server.pipeline().addFilter(new GunNettyStdFirstFilter(new GunNettyDefaultObserve())).
                addFilter(new GunNettyCharsetInboundChecker()).
                setHandle(new GunNettyStringHandle());
        server.setSyncType(false);
        server.sync();
        //running time
        Thread.sleep(1000);
        server.stop();
    }
}
