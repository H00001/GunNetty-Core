package top.gunplan.netty;

import top.gunplan.netty.common.GunNettyContext;

import java.net.SocketAddress;

/**
 * GunNettyBaseObserve
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-05 00:19
 */
public interface GunNettyBaseObserve {
    /**
     * onListen happened
     *
     * @param port port
     */
    default void onListen(int port) {
        GunNettyContext.logger.info("server running on :" + port);
    }

    default int preReadClose(SocketAddress address) {
        GunNettyContext.logger.debug("Client closed:" + address.toString(), "[CONNECTION]");
        return 0;
    }

    default int preWriteClose(SocketAddress address) {
        return 0;
    }

}
