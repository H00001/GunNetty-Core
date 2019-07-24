package top.gunplan.netty.test;

import org.junit.jupiter.api.Test;
import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunException;
import top.gunplan.netty.GunNettyHandle;
import top.gunplan.netty.common.GunNettyExecutors;
import top.gunplan.netty.impl.GunBootServerFactory;
import top.gunplan.netty.impl.GunNettyStdFirstFilter;
import top.gunplan.netty.protocol.GunNetInbound;
import top.gunplan.netty.protocol.GunNetOutBound;

import java.net.SocketAddress;

public class BaseTest {
    @Test
    public void using019() throws InterruptedException {

        GunBootServer server = GunBootServerFactory.getInstance();
        server.setExecutors(GunNettyExecutors.newFixedExecutorPool(10), GunNettyExecutors.newFixedExecutorPool(10));
        server.pipeline().addFilter(new GunNettyStdFirstFilter()).setHandle(new GunNettyHandle() {
            @Override
            public GunNetOutBound dealDataEvent(GunNetInbound request) throws GunException {
                return null;
            }

            @Override
            public GunNetOutBound dealConnEvent(SocketAddress address) throws GunException {
                return null;
            }

            @Override
            public void dealCloseEvent() {

            }

            @Override
            public void dealExceptionEvent(GunChannelException exp) {

            }
        });
        server.setSyncType(false);

        server.sync();

        Thread.sleep(0b11111010000);


        server.stop();
    }
}
