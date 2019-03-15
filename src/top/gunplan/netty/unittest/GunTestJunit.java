package top.gunplan.netty.unittest;

import org.junit.jupiter.api.Test;
import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.filters.HttpModelFilter;
import top.gunplan.netty.impl.GunBootServerFactory;
import top.gunplan.netty.impl.example.GunOutputHander;

import java.io.IOException;
import java.util.concurrent.Executors;


/**
 * @author dosdrtt
 */
public class GunTestJunit {
    @Test
    public void doTest() throws Exception {
//        CountDownLatch latch = new CountDownLatch(1);
//        latch.await();
//        System.out.println("gg");
        GunBootServer server = GunBootServerFactory.getInstance(8822);
        server.setExecuters(Executors.newFixedThreadPool(3),Executors.newFixedThreadPool(3)).addFilter(new HttpModelFilter()).setHandel(new GunOutputHander());
        server.sync();
    }

    public static void main(String[] args) throws IOException {

    }
//        GunBootServer server = GunBootServerFactory.getInstance(8822);
//       // server.setExecuters(Executors.newFixedThreadPool(3),Executors.newFixedThreadPool(3));
//        //server.setHandel(new GunOutputHander());
//        server.sync();

}