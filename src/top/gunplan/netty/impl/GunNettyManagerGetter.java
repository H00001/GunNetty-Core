package top.gunplan.netty.impl;

/**
 * GunNettyManagerGetter
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-24 00:38
 */
public interface GunNettyManagerGetter<K> {
    /**
     * register manager
     *
     * @param manager manager
     * @return return chain
     */
    K registerManager(GunNettyCoreThreadManager manager);
}
