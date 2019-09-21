/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

import top.gunplan.netty.GunBootServer;

/**
 * GunBootServerFactory
 * create GunBootServer
 *
 * @author dosdrtt
 */
public class GunBootServerFactory {
    public static GunBootServer newInstance() {
        return new GunBootServerImpl();
    }
}
