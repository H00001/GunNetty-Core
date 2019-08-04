package top.gunplan.netty;

import top.gunplan.netty.common.GunNettyContext;

/**
 * GunNettyBaseObserve
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-05 00:19
 */
public interface GunNettyBaseObserve {
    default void onListion(int port) {
        GunNettyContext.logger.info("server running on :" + port);
    }
}
