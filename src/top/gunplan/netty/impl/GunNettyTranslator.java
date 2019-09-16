/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.protocol.GunNetInbound;

/**
 * GunNettyTranslator
 *
 * @author frank albert
 * @version 0.0.0.1
 * # 2019-06-29 21:46
 */

public interface GunNettyTranslator {


    /**
     * tranToObject
     * translate a class to a object by src
     *
     * @param in  class Class
     * @param <R> GunNetInbound {@link  GunNetInbound}
     * @return boolean
     */
    <R extends GunNetInbound> boolean tranToObject(Class<R> in);

}
