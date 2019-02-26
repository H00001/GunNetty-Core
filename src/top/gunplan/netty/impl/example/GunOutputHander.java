package top.gunplan.netty.impl.example;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.BaseGunLog;
import top.gunplan.netty.anno.Order;
import top.gunplan.nio.utils.ReadByteBuffer;

@Order(index = 0)
public class GunOutputHander implements GunBootServer.GunNetHander {
    {
        BaseGunLog.setLevel(0);
    }
    @Override
    public void dealevent(EventType t, GunBootServer.C3B4DTO m) throws Exception {
        if (t == EventType.RECEIVED) {
             BaseGunLog.info("RECEIVED "+ReadByteBuffer.readToString(m.getBf()));
        }
        else if (t==EventType.CONNRCTED)
        {
            BaseGunLog.info("CONNECTED ");
        }
        else if (t==EventType.CLOSEED)
        {
            BaseGunLog.info("CLOSED ");
        }
    }
}
