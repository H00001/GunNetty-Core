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
    ConnEventLoopBuilder port(int p) throws IOException;
}
