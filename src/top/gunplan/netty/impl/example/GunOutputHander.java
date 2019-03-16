package top.gunplan.netty.impl.example;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.filters.protocls.GunHttpProtocl;
import top.gunplan.nio.utils.BaseGunLog;

import java.io.IOException;

public class GunOutputHander implements GunBootServer.GunNetHandle {
    {
        BaseGunLog.setLevel(0);
    }

    @Override
    public void dealevent(EventType t, GunBootServer.GunNettyRequestObject m) {
        switch (t) {
            case RECEIVED:
                if (m.requestObj().getGunRequestProtoclObject() instanceof GunHttpProtocl) {
                    GunHttpProtocl httpProtocl = ((GunHttpProtocl) m.requestObj().getGunRequestProtoclObject());
                    httpProtocl.getRequstHead().forEach((s, s2) ->
                            BaseGunLog.info(s + " " + "->" + " " + s2)
                    );
                }
                break;

            case CONNRCTED:
                try {
                    BaseGunLog.error("CONNECTED " + m.getChannel().getRemoteAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case CLOSEED:
                BaseGunLog.error("CLOSED ");
                break;
            default:


        }

    }
}

