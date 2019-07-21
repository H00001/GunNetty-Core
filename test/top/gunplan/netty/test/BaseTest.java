package top.gunplan.netty.test;

import org.junit.Test;
import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.impl.GunBootServerFactory;

public class BaseTest {
    @Test
    public void doTest() {
        GunBootServer server = GunBootServerFactory.getInstance();
        server.setExecuters(GunNettyExecutors.newFixedExecutorPool(10), GunNettyExecutors.newFixedExecutorPool(10)).
                sync();
    }
}
