package top.gunplan.netty;

import top.gunplan.netty.impl.GunNettyCoreThreadManager;
import top.gunplan.netty.impl.propertys.GunLogProperty;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.netty.impl.propertys.base.GunNettyPropertyManager;
import top.gunplan.netty.impl.propertys.base.GunNettyPropertyManagerFactory;

public final class GunNettySystemServices {
    public final static GunNettyPropertyManager PROPERTY_MANAGER = GunNettyPropertyManagerFactory.propertyInstance();


    public static GunNettyCoreProperty coreProperty() {
        return PROPERTY_MANAGER.getProperty(GunNettyCoreProperty.class);
    }

    public static final GunNettyCoreThreadManager CORE_THREAD_MANAGER = GunNettyCoreThreadManager.initInstance();

    public static GunLogProperty logProperty() {
        return PROPERTY_MANAGER.getProperty(GunLogProperty.class);
    }
}
