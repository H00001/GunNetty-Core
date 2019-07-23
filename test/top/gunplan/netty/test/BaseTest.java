package top.gunplan.netty.test;

import org.junit.jupiter.api.Test;
import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.impl.GunBootServerFactory;

public class BaseTest {
    @Test
    public void using019() {
        GunBootServer server = GunBootServerFactory.getInstance();
    }
}
