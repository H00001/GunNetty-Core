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
     * @throws GunException std exception
     * @throws IOException  error
     */
    GunNetResponseInterface dealDataEvent(GunNetRequestInterface request) throws GunException, IOException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    /**
     * @throws GunException,IOException
     * @throws IOException              conn failt
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
