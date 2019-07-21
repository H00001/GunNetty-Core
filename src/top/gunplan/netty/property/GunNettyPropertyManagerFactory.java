package top.gunplan.netty.property;

/**
 * GunNettyPropertyManagerFactory
 *
 * @author frank albert
 * @date 2019-07-21 14:50
 *
 * @version 0.0.0.1
 */

public class GunNettyPropertyManagerFactory {
    private static GunNettyPropertyManager manager;

    public static synchronized GunNettyPropertyManager propertyInstance() {
        if (manager != null) {
            manager = new GunNettyPropertyManagerImpl();
        }
        return manager;
    }
}
