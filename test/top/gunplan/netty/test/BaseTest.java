package top.gunplan.netty.test;

import org.junit.jupiter.api.Test;
import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.example.GunNettyCharsetInboundChecker;
import top.gunplan.netty.example.GunNettyStringHandle;
import top.gunplan.netty.impl.GunBootServerFactory;
import top.gunplan.netty.impl.GunNettyStdFirstFilter;

public class BaseTest {
    @Test
    public void using019() throws InterruptedException {

        GunBootServer server = GunBootServerFactory.getInstance();
        server.setExecutors(GunNettyExecutors.newFixedExecutorPool(10), GunNettyExecutors.newFixedExecutorPool(10));
        server.pipeline().addFilter(new GunNettyStdFirstFilter()).addFilter(new GunNettyCharsetInboundChecker()).setHandle(new GunNettyStringHandle());
        server.setSyncType(false);
        server.sync();

        Thread.sleep(0b1111111111111010000);


        server.stop();
    }
}
