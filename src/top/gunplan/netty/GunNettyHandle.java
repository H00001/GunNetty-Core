package top.gunplan.netty;

import top.gunplan.netty.protocol.GunNetInputInterface;
import top.gunplan.netty.protocol.GunNetResponseInterface;

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
     * @return GunNetResponseInterface
     * @throws GunException kinds of exception
     */
    GunNetResponseInterface dealDataEvent(GunNetInputInterface request) throws GunException;

    /**
     * dealConnEvent connection event
     *
     * @param request request object
     * @return GunNetResponseInterface
     * @throws GunException kinds of exception
     * @throws IOException  IO error
     */
    GunNetResponseInterface dealConnEvent(SocketChannel request) throws GunException;

    /**
     *
     */
    void dealCloseEvent();

    /**
     * @param exp Exception
     */
    void dealExceptionEvent(Exception exp);
}
