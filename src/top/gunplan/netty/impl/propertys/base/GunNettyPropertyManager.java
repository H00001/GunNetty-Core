package top.gunplan.netty.impl.propertys.base;

import top.gunplan.netty.GunProperty;
import top.gunplan.netty.impl.propertys.GunPropertyStrategy;

/**
 * @author dosdrtt
 */
public interface GunNettyPropertyManager {

    void analyzingProperties();

    GunNettyPropertyManager setStrategy(GunPropertyStrategy strategy);

    <T extends GunProperty> T getProperty(Class<T> clazz);

    void registerProperty(GunProperty property);


    boolean initProperty();
}