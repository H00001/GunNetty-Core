package top.gunplan.netty;

import top.gunplan.netty.protocol.GunNetInbound;
import top.gunplan.netty.protocol.GunNetOutbound;

import java.net.SocketAddress;

/**
 * net handle is a handle interface used to deal event
 *
 * @author dosdrtt
 */
public interface GunNettyHandle extends GunHandle {
    /**
     * dealDataEvent
     *
     * @param request GunNetInbound
     * @return GunNetOutbound
     * @throws GunException kinds of exception
     */
    GunNetOutbound dealDataEvent(GunNetInbound request) throws GunException;

    /**
     * dealConnEvent connection event
     *
     * @param address request address information
     * @return GunNetOutbound
     * @throws GunException kinds of exception
     * @throws GunException IO error
     */
    GunNetOutbound dealConnEvent(SocketAddress address) throws GunException;

    /**
     * when close event happened ,the method will be called
     */
    void dealCloseEvent();

    /**
     * when exception event happened ,the method will be called
     *
     * @param exp Exception
     */
    void dealExceptionEvent(GunChannelException exp);
}
