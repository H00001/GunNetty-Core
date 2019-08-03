package top.gunplan.netty.impl.propertys;

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

public interface GunNettyPropertyAnalyzier {

    /**
     * set property
     *
     * @param propertiesMap propertiesMap
     * @param info          next information
     */
    void nextAnalyzing(Map<String, GunProperty> propertiesMap, String info) throws GunBootServerBase.GunNettyCanNotBootException;

    /**
     * analyzing properties
     *
     * @param properties    propertiesMap
     * @param propertiesMap propertiesMap
     * @throws NoSuchFieldException   reference error
     * @throws IllegalAccessException reference error
     */
    void analyzingProperties(String[] properties, Map<String, GunProperty> propertiesMap) throws NoSuchFieldException, IllegalAccessException;
}
