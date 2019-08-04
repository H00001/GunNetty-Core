package top.gunplan.netty;

import top.gunplan.netty.impl.propertys.GunLogProperty;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.netty.impl.propertys.base.GunNettyPropertyManager;
import top.gunplan.netty.impl.propertys.base.GunNettyPropertyManagerFactory;

/**
 * GunNettySystemServices
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-27 08:08
 */

public final class GunNettySystemServices {
    public final static GunNettyPropertyManager PROPERTY_MANAGER = GunNettyPropertyManagerFactory.
            propertyInstance();


    public static GunNettyCoreProperty coreProperty() {
        return PROPERTY_MANAGER.acquireProperty(GunNettyCoreProperty.class);
    }

    public static GunLogProperty logProperty() {
        return PROPERTY_MANAGER.acquireProperty(GunLogProperty.class);
    }
}
