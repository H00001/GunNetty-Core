package top.gunplan.netty.impl.example;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunNetHandle;
import top.gunplan.netty.protocol.GunNetRequestInterface;
import top.gunplan.netty.protocol.GunNetResponseInterface;
import top.gunplan.netty.protocol.GunStdString;
import top.gunplan.nio.utils.AbstractGunBaseLogUtil;

public class GunOutputHander implements GunNetHandle {


    @Override
    public GunNetResponseInterface dealDataEvent(GunNetRequestInterface m) {
        if (m instanceof GunStdString) {
            GunStdString httpProtocl = ((GunStdString) m);
            AbstractGunBaseLogUtil.urgency(httpProtocl.getString());
        }
        return null;
    }

    @Override
    public GunNetResponseInterface dealConnEvent(GunNetRequestInterface m) throws GunException {
        AbstractGunBaseLogUtil.error("CONNECTED ");
        return null;
    }

    @Override
    public void dealCloseEvent() {
        AbstractGunBaseLogUtil.error("CLOSED ");
    }

    @Override
    public void dealExceptionEvent(Exception exp) {

    }


}

