package top.gunplan.netty.unittest;

import org.junit.jupiter.api.Test;
import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.impl.GunAsyncGunInforHander;
import top.gunplan.netty.impl.GunBootServerFactory;
import top.gunplan.netty.impl.example.GunOutputHander;


/**
 * @author dosdrtt
 */
public class GunTestJunit {
    @Test
    public void doTest() throws Exception {
        GunBootServer server = GunBootServerFactory.getInstance(8822);
        server.infor(new GunOutputHander());
        server.setInforHander(new GunAsyncGunInforHander());
        server.sync();
    }
}