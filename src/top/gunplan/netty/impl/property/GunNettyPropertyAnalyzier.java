/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty.impl.property;

import top.gunplan.netty.GunBootServerBase;
import top.gunplan.netty.GunProperty;

import java.util.Map;

/**
 * GunNettyPropertyAnalyzier
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-03 18:22
 */

public interface GunNettyPropertyAnalyzier<U, R> {

    /**
     * set property
     *
     * @param propertiesMap propertiesMap
     * @param info          next information
     */
    void nextAnalyzing(Map<String, GunProperty> propertiesMap, U info) throws GunBootServerBase.GunNettyCanNotBootException;

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
