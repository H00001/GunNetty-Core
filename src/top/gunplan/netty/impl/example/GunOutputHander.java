package top.gunplan.netty.impl.example;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunException;
import top.gunplan.netty.protocol.GunHttp2RequestProtocl;
import top.gunplan.netty.protocol.GunNetRequestInterface;
import top.gunplan.netty.protocol.GunNetResponseInterface;
import top.gunplan.nio.utils.BaseGunLog;

import java.io.IOException;

public class GunOutputHander implements GunBootServer.GunNetHandle {
    {
        BaseGunLog.setLevel(0);
    }

    @Override
    public GunNetResponseInterface dealDataEvent(GunNetRequestInterface m) {
        if (m instanceof GunHttp2RequestProtocl) {
            GunHttp2RequestProtocl httpProtocl = ((GunHttp2RequestProtocl) m);
            httpProtocl.getRequstHead().forEach((s, s2) ->
                    BaseGunLog.info(s + " " + "->" + " " + s2)
            );
        }
        return null;
    }

    @Override
    public GunNetResponseInterface dealConnEvent(GunNetRequestInterface m) throws GunException {
        BaseGunLog.error("CONNECTED ");
        return null;
    }

    @Override
    public void dealCloseEvent() {
        BaseGunLog.error("CLOSED ");
    }

    @Override
    public void dealExceptionEvent(Exception exp) {

    }


}

