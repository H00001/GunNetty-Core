package top.gunplan.netty;

import top.gunplan.netty.protocol.GunNetInputInterface;
import top.gunplan.netty.protocol.GunNetOutputInterface;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * net handle is a handle interface used to deal event
 *
 * @author dosdrtt
 */
public interface GunNettyHandle extends GunHandle {
    /**
     * @param request GunNetInputInterface
     * @return GunNetOutputInterface
     * @throws GunException kinds of exception
     */
    GunNetOutputInterface dealDataEvent(GunNetInputInterface request) throws GunException;

    /**
     * dealConnEvent connection event
     *
     * @param request request object
     * @return GunNetOutputInterface
     * @throws GunException kinds of exception
     * @throws IOException  IO error
     */
    GunNetOutputInterface dealConnEvent(SocketChannel request) throws GunException;

    /**
     *
     */
    void dealCloseEvent();

    /**
     * @param exp Exception
     */
    void dealExceptionEvent(Exception exp);
}
