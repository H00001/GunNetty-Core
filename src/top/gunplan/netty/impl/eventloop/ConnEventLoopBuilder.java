package top.gunplan.netty.impl.eventloop;

import java.io.IOException;

/**
 * ConnEventLoopBuilder
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-23 00:17
 */
public interface ConnEventLoopBuilder extends EventLoopBuilder<GunConnEventLoop> {
    /**
     * bindPort
     *
     * @param p port
     * @return this chain style
     * @throws IOException when bind file
     */
    ConnEventLoopBuilder bindPort(int p) throws IOException;
}
