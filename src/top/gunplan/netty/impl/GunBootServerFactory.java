package top.gunplan.netty.impl;

import top.gunplan.netty.GunBootServer;

/**
 * create GunBootServer
 *
 * @author dosdrtt
 */
public class GunBootServerFactory {
    public static GunBootServer getInstance() {
        return new GunBootServerImpl(GunNettySupportParameter.Companion.getPort());
    }

    public static GunBootServer getInstance(int port) {
        return new GunBootServerImpl(port);
    }
}
