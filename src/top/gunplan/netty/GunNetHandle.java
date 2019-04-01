package top.gunplan.netty;

import top.gunplan.netty.protocol.GunNetRequestInterface;
import top.gunplan.netty.protocol.GunNetResponseInterface;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * net handle is a handle interface used to deal event
 *
 * @author dosdrtt
 */
public interface GunNetHandle extends GunHandle {
    /**
     * @param request GunNetRequestInterface
     * @return GunNetResponseInterface
     * @throws GunException kinds of exception
     */
    GunNetResponseInterface dealDataEvent(GunNetRequestInterface request) throws GunException;

    /**
     * dealConnEvent connection event
     *
     * @param request request object
     * @return GunNetResponseInterface
     * @throws GunException kinds of exception
     * @throws IOException  IO error
     */
    GunNetResponseInterface dealConnEvent(GunNetRequestInterface request) throws GunException;

    /**
     *
     */
    void dealCloseEvent();

    /**
     * @param exp Exception
     */
    void dealExceptionEvent(Exception exp);
}
