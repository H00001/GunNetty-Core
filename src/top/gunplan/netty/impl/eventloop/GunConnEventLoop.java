package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunCoreEventLoop;

import java.io.IOException;

/**
 * GunConnEventLoop
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-23 00:11
 */
public interface GunConnEventLoop extends GunCoreEventLoop {
    int openPort(int port) throws IOException;

    int listenPort();
}
