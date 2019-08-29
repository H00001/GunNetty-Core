/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty.impl.property.base;

/**
 * GunNettyPropertyManagerFactory
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-07-21 14:50
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
