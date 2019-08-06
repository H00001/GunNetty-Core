/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property;

import top.gunplan.netty.GunBootServerBase;
import top.gunplan.netty.GunProperty;

import java.util.Map;

/**
 * GunNettyPropertyAnalyzer
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-03 18:22
 */

public interface GunNettyPropertyAnalyzer<U, R> {

    /**
     * nextAnalyze
     * <p>
     * set property
     *
     * @param propertiesMap propertiesMap
     * @param info          next information
     * @throws GunBootServerBase.GunNettyCanNotBootException i/o or analyze error
     */
    void nextAnalyze(Map<String, GunProperty> propertiesMap, U info) throws GunBootServerBase.GunNettyCanNotBootException;

    /**
     * analyzing properties
     *
     * @param properties    properties meta data
     * @param propertiesMap propertiesMap
     * @throws NoSuchFieldException   reference error
     * @throws IllegalAccessException reference error
     */
    void analyzingProperties(R properties, Map<String, GunProperty> propertiesMap) throws NoSuchFieldException, IllegalAccessException;
}
