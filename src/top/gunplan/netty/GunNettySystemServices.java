/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty;

import top.gunplan.netty.impl.property.GunLogProperty;
import top.gunplan.netty.impl.property.GunNettyCoreProperty;
import top.gunplan.netty.impl.property.base.GunNettyPropertyManager;
import top.gunplan.netty.impl.property.base.GunNettyPropertyManagerFactory;

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
