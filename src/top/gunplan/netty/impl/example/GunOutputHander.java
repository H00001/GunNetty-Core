package top.gunplan.netty.impl.example;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunException;
import top.gunplan.netty.filters.protocls.GunHttp2Protocl;
import top.gunplan.nio.utils.BaseGunLog;

import java.io.IOException;

public class GunOutputHander implements GunBootServer.GunNetHandle {
    {
        BaseGunLog.setLevel(0);
    }

    @Override
    public void dealDataEvent(EventType t, GunBootServer.GunNettyRequestObject m) {
        switch (t) {
            case RECEIVED:
                if (m.requestObj().getGunRequestProtoclObject() instanceof GunHttp2Protocl) {
                    GunHttp2Protocl httpProtocl = ((GunHttp2Protocl) m.requestObj().getGunRequestProtoclObject());
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

    @Override
    public void dealConnEvent(EventType t, GunBootServer.GunNettyRequestObject m) throws GunException, IOException {

    }

    @Override
    public void dealCloseEvent(EventType t) throws GunException, IOException {

    }

    @Override
    public void dealExceptionEvent(EventType t) {

    }
}

