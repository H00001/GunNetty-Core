package top.gunplan.netty.unittest;

import org.junit.jupiter.api.Test;
import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.filters.GunStdHttp2Filter;
import top.gunplan.netty.handles.GunStdHttpHandle;
import top.gunplan.netty.impl.GunBootServerFactory;

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
        GunBootServer server = GunBootServerFactory.getInstance();
        server.setExecuters(Executors.newFixedThreadPool(10), Executors.newFixedThreadPool(10)).addFilter(new GunStdHttp2Filter()).setHandel(new GunStdHttpHandle("top.gunplan.netty.test"));
        server.sync();
    }

    public static void main(String[] args) throws IOException {

    }
//        GunBootServer server = GunBootServerFactory.getInstance(8822);
//       // server.setExecuters(Executors.newFixedThreadPool(3),Executors.newFixedThreadPool(3));
//        //server.setHandel(new GunOutputHander());
//        server.sync();

}