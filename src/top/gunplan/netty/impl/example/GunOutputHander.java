package top.gunplan.netty.impl.example;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunNettyHandle;
import top.gunplan.netty.protocol.GunNetInputInterface;
import top.gunplan.netty.protocol.GunNetOutputInterface;
import top.gunplan.netty.protocol.GunStdString;
import top.gunplan.utils.AbstractGunBaseLogUtil;
import java.nio.channels.SocketChannel;

/**
 * GunOutputHander
 */
public class GunOutputHander implements GunNettyHandle {


    @Override
    public GunNetOutputInterface dealDataEvent(GunNetInputInterface m) {
        if (m instanceof GunStdString) {
            GunStdString httpProtocl = ((GunStdString) m);
            AbstractGunBaseLogUtil.urgency(httpProtocl.getString());
        }
        return null;
    }

    @Override
    public GunNetOutputInterface dealConnEvent(SocketChannel channel) throws GunException {
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

