package top.gunplan.netty.impl;

import top.gunplan.netty.GunNetHandle;

import java.nio.channels.SocketChannel;

abstract class BaseGunNettyWorker implements GunNettyWorkerInterface{
    final GunNetHandle handel;
    final SocketChannel channel;
    BaseGunNettyWorker(final GunNetHandle gunNettyHanderl, final SocketChannel channel) {
        this.channel = channel;
        this.handel = gunNettyHanderl;
    }
}
