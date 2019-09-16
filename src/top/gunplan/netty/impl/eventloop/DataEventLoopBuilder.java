package top.gunplan.netty.impl.eventloop;

import java.nio.channels.SocketChannel;

/**
 * DataEventLoopBuilder
 *
 * @author frank albert
 * @version 0.0.0.5
 */
public interface DataEventLoopBuilder extends EventLoopBuilder<GunDataEventLoop<SocketChannel>[]> {
    /**
     * sudo
     * it should have something
     */
    default void sudo() {
    }


}
