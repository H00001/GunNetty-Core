package top.gunplan.netty.impl.propertys.base;

import top.gunplan.netty.GunProperty;
import top.gunplan.netty.impl.propertys.GunPropertyStrategy;

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
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends GunProperty> T acquireProperty(Class<T> clazz);

    void registerProperty(GunProperty property);


    boolean initProperty();
}