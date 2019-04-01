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
     * @param request
     * @return GunNetResponseInterface
     * @throws GunException kinds of exception
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    GunNetResponseInterface dealDataEvent(GunNetRequestInterface request) throws GunException, IOException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    /**
     * @param request
     * @return GunNetResponseInterface
     * @throws GunException
     * @throws IOException
     */
    GunNetResponseInterface dealConnEvent(GunNetRequestInterface request) throws GunException, IOException;

    /**
     *
     */
    void dealCloseEvent();

    /**
     * @param exp Exception
     */
    void dealExceptionEvent(Exception exp);
}
