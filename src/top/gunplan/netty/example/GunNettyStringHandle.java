package top.gunplan.netty.example;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunException;
import top.gunplan.netty.GunNettyHandle;
import top.gunplan.netty.protocol.GunNetInbound;
import top.gunplan.netty.protocol.GunNetOutBound;

import java.net.SocketAddress;


public class GunNettyStringHandle implements GunNettyHandle {
    @Override
    public GunNetOutBound dealDataEvent(GunNetInbound request) throws GunException {
        return (GunNetOutBound) request;
    }

    @Override
    public GunNetOutBound dealConnEvent(SocketAddress address) throws GunException {
        return null;
    }

    @Override
    public void dealCloseEvent() {

    }

    @Override
    public void dealExceptionEvent(GunChannelException exp) {

    }
}
