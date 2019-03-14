package top.gunplan.netty.impl.example;

import top.gunplan.netty.GunBootServer;
import top.gunplan.nio.utils.BaseGunLog;
import top.gunplan.netty.anno.GunNetHanderOrder;
import top.gunplan.nio.utils.ReadByteBuffer;

import java.io.IOException;

@GunNetHanderOrder(index = 0)
public class GunOutputHander implements GunBootServer.GunNetHander {
    {
        BaseGunLog.setLevel(0);
    }

    @Override
    public void dealevent(EventType t, GunBootServer.GunNettyRequestOnject m) {
        switch (t) {
            case RECEIVED:
                BaseGunLog.info("RECEIVED " + new String(m.requestObj().getSrc()));
                break;

            case CONNRCTED:
                try {
                    BaseGunLog.info("CONNECTED " + m.getChannel().getRemoteAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case CLOSEED:
                BaseGunLog.info("CLOSED ");
                break;
                default:


        }

    }
}

