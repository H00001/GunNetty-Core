package top.gunplan.netty.impl.propertys;

import top.gunplan.netty.GunProperty;

import java.util.Map;

/**
 * GunPropertyStrategy
 * <p>
 * this interface is only for acquireProperty
 * as for how to give them to property value
 * ti should by manage
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-25 20:45
 */
@FunctionalInterface
public interface GunPropertyStrategy {
    /**
     * settingProperties
     *
     * @param propertyMap propertyMap to Set
     * @return set result
     */
    boolean settingProperties(Map<String, GunProperty> propertyMap);
}
