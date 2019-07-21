package top.gunplan.netty.impl.propertys.base;

/**
 * GunNettyPropertyManagerFactory
 *
 * @author frank albert
 * @version 0.0.0.1
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
