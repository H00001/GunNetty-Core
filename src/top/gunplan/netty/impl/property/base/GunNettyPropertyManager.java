/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
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
     * have been registered in manager
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