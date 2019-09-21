/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property.base;

/**
 * GunNettyPropertyManagerFactory
 *
 * @author frank albert
 * @version 0.0.0.2
 * # 2019-07-21 14:50
 */

public class GunNettyPropertyManagerFactory {
    private static GunNettyPropertyManager manager;

    public static synchronized GunNettyPropertyManager propertyInstance() {
        if (manager == null) {
            manager = new GunNettyPropertyManagerImpl();
        }
        return manager;
    }
}
