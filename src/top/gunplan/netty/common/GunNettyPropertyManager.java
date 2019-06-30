package top.gunplan.netty.common;

import top.gunplan.netty.impl.propertys.GunPropertyStrategy;

/**
 * @author dosdrtt
 */
public interface GunNettyPropertyManager {

    void analyzingProperties();

    GunNettyPropertyManager setStrategy(GunPropertyStrategy strategy);

}
