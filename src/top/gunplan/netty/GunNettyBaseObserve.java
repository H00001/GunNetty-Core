/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.common.GunNettyContext;

import java.io.IOException;
import java.net.SocketAddress;

/**
 * GunNettyBaseObserve
 *
 * @author frank albert
 * @version 0.0.0.w
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

    /**
     * Ready-to-read termination
     *
     * @param address remote address
     * @return wait time
     */
    default int preReadClose(SocketAddress address) {
        output(address);
        return 0;
    }

    /**
     * output
     *
     * @param address remote address
     */
    private void output(SocketAddress address) {
        GunNettyContext.logger.debug("client closed:" + address.toString(), "[CONNECTION]");
    }

    /**
     * Ready-to-write termination
     *
     * @param address remote address
     * @return wait time
     */
    default int preWriteClose(SocketAddress address) {
        output(address);
        return 0;
    }

    /**
     * bootFail happened
     *
     * @param exp error
     * @apiNote #4043
     */
    default void bootFail(IOException exp) {
        GunNettyContext.logger.setTAG(this.getClass()).urgency(exp.getMessage());
    }


    /**
     * bootFail happened
     *
     * @param exp error
     * @apiNote #4043
     */
    default void runningError(Exception exp) {
        GunNettyContext.logger.setTAG(this.getClass()).urgency(exp.getMessage());
    }
}
