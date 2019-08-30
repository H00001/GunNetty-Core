/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.test;

import org.junit.jupiter.api.Test;
import top.gunplan.netty.*;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.example.GunNettyCharsetInboundChecker;
import top.gunplan.netty.example.GunNettyStringHandle;
import top.gunplan.netty.filter.GunNettyInboundFilter;
import top.gunplan.netty.impl.GunBootServerFactory;
import top.gunplan.netty.impl.GunNettyDefaultObserve;
import top.gunplan.netty.impl.GunNettyStdFirstFilter;
import top.gunplan.netty.impl.checker.GunInboundChecker;
import top.gunplan.netty.impl.property.GunGetPropertyFromNet;

import java.util.concurrent.atomic.AtomicInteger;

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
            pipeline.setMetaInfoChangeObserver(new DefaultGunNettyChildrenPipelineChangedObserve());
            pipeline.addDataFilter(new GunNettyStdFirstFilter(p));
            pipeline.addDataFilter(new GunNettyCharsetInboundChecker());
            pipeline.addConnFilter(new GunNettyStdFirstFilter(p));
            pipeline.addDataFilter(new GunNettyInboundFilter() {
                AtomicInteger i = new AtomicInteger(0);

                @Override
                public DealResult doInputFilter(GunInboundChecker filterDto) throws GunChannelException {
                    i.incrementAndGet();
                    if (i.get() == 10) {
                        filterDto.channel().closeAndRemove(true);
                        return DealResult.CLOSED;
                    }
                    return DealResult.NEXT;
                }


            });
            pipeline.setHandle((GunNettyChildrenHandle) new GunNettyStringHandle());
            pipeline.setHandle((GunNettyParentHandle) new GunNettyStringHandle());
        });
        server.whenServerChannelStateChanged(new SystemChannelChangedHandle() {

        });
        server.setSyncType(false);
        server.sync();
        //running time
        Thread.sleep(1000000);
        server.stop();
    }
}
