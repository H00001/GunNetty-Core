package top.gunplan.netty.impl.example;

import top.gunplan.netty.GunBootServer;
import top.gunplan.nio.utils.BaseGunLog;
import top.gunplan.netty.anno.GunNetFilterOrder;

import java.io.IOException;
import java.util.HashMap;

@GunNetFilterOrder(index = 0)
public class GunOutputHander implements GunBootServer.GunNetHandel {
    {
        BaseGunLog.setLevel(0);
    }
    @Override
    public void dealevent(EventType t, GunBootServer.GunNettyRequestOnject m) {
        switch (t) {
            case RECEIVED:

                for (String k : ((HashMap<String, String>) m.requestObj().getObject()).keySet()) {
                    BaseGunLog.info("RECEIVED Host:" + k + ":");
                }

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

