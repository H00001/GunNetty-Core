package top.gunplan.netty;

import top.gunplan.netty.impl.propertys.GunLogProperty;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.netty.property.GunNettyPropertyManager;
import top.gunplan.netty.property.GunNettyPropertyManagerFactory;

public class GunNettySystemServices {
    public final static GunNettyPropertyManager PROPERTY_MANAGER = GunNettyPropertyManagerFactory.propertyInstance();


    public static GunNettyCoreProperty coreProperty() {
        return PROPERTY_MANAGER.getProperty(GunNettyCoreProperty.class);
    }

    public static GunLogProperty logProperty() {
        return PROPERTY_MANAGER.getProperty(GunLogProperty.class);
    }
}
