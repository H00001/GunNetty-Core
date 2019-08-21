/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.test;

import org.junit.jupiter.api.Test;
import top.gunplan.netty.*;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.example.GunNettyCharsetInboundChecker;
import top.gunplan.netty.example.GunNettyStringHandle;
import top.gunplan.netty.impl.GunBootServerFactory;
import top.gunplan.netty.impl.GunNettyDefaultObserve;
import top.gunplan.netty.impl.GunNettyStdFirstFilter;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;
import top.gunplan.netty.impl.property.GunGetPropertyFromNet;
import top.gunplan.netty.observe.GunNettyChildrenPipelineChangedObserve;

public class BaseTest {

    public void preTest() {

    }

    @Test
    public void using019() throws InterruptedException {
        GunNettyDefaultObserve p = new GunNettyDefaultObserve();
        GunNettySystemServices.PROPERTY_MANAGER.setStrategy(new GunGetPropertyFromNet("https://p.gunplan.top/config1.html"));
        GunBootServer server = GunBootServerFactory.newInstance();
        server.setExecutors(GunNettyExecutors.newFixedExecutorPool(10),
                GunNettyExecutors.newFixedExecutorPool(10));
        server.registerObserve(new GunNettyDefaultObserve());
        server.registerObserve(p).onHasChannel(pipeline -> {
            pipeline.setMetaInfoChangeObserver(new GunNettyChildrenPipelineChangedObserve() {

                @Override
                public void onUpdateHandle(GunNettyChildrenHandle handle, GunNettyChildrenPipeline pipeline) {
                    GunNettyContext.logger.info("handle has been added:" + handle);
                }

                @Override
                public void onUpdateHandle(GunNettyParentHandle handle, GunNettyChildrenPipeline pipeline) {
                    GunNettyContext.logger.info("handle has been added:" + handle);
                }

                @Override
                public <V extends GunNettyFilter> void onAddFilter(V filter, GunNettyChildrenPipeline pipeline) {
                    GunNettyContext.logger.info("filter has been added:" + filter);
                }

                @Override
                public <V extends GunNettyFilter> void onRemoveFilter(V filter, GunNettyChildrenPipeline pipeline) {
                    GunNettyContext.logger.info("filter has been removed" + filter);
                }
            });
            pipeline.addDataFilter(new GunNettyStdFirstFilter(p));
            pipeline.addDataFilter(new GunNettyCharsetInboundChecker());
            pipeline.addConnFilter(new GunNettyStdFirstFilter(p));
            pipeline.setHandle((GunNettyChildrenHandle) new GunNettyStringHandle());
            pipeline.setHandle((GunNettyParentHandle) new GunNettyStringHandle());
        });
        server.whenServerChannelStateChanged(new SystemChannelChangedHandle() {

        });
        server.setSyncType(false);
        server.sync();
        //running time
        Thread.sleep(10000000);
        server.stop();
    }
}
