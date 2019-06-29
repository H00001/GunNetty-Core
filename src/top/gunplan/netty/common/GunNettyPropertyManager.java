package top.gunplan.netty.common;

import top.gunplan.netty.impl.propertys.GunPropertyStrategy;

/**
 * @author dosdrtt
 */
public interface GunNettyPropertyManager {
    //public static boolean initProperty();
    void analyzingProperties();


    GunNettyPropertyManager setStrategy(GunPropertyStrategy strategy);

}
