/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property.base;

import top.gunplan.netty.GunProperty;
import top.gunplan.netty.impl.property.GunPropertyStrategy;

/**
 * @author dosdrtt
 * @date 2019-04-13
 * @since 0.0.0.1
 */
public interface GunNettyPropertyManager {

    /**
     * analyzingProperties
     * analyzing properties: give value to property class that
     * have been registered in threadManager
     */
    void analyzingProperties();

    /**
     * setStrategy
     *
     * @param strategy GunPropertyStrategy strategy kinds of strategy
     * @return this chain style
     */
    GunNettyPropertyManager setStrategy(GunPropertyStrategy strategy);


    /**
     * acquireProperty
     *
     * @param clazz property class
     * @param <T>   property class
     * @return property object
     */
    <T extends GunProperty> T acquireProperty(Class<T> clazz);

    /**
     * registerProperty
     *
     * @param property GunProperty
     */
    void registerProperty(GunProperty property);


    /**
     * init
     * @return init result
     */
    boolean initProperty();
}