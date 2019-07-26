package top.gunplan.netty.impl;

import top.gunplan.netty.protocol.GunNetInbound;

/**
 * GunNettyTranslator
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-29 21:46
 */

public interface GunNettyTranslator {
    /**
     * translator data
     * from byte[] to object or
     * from object ro byte[]
     */
    void translate();


    /**
     * tranToObject
     * translate a class to a object by src
     *
     * @param in  Class<R> class
     * @param <R> GunNetInbound {@link  GunNetInbound}
     * @return boolean
     * @throws ReflectiveOperationException reflective exception
     */
    <R extends GunNetInbound> boolean tranToObject(Class<R> in) throws ReflectiveOperationException;

}
