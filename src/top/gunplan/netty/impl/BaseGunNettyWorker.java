package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyHandle;
import top.gunplan.netty.GunPilelineInterface;

import java.nio.channels.SocketChannel;

abstract class BaseGunNettyWorker implements GunNettyWorkerInterface {
    final GunPilelineInterface pileline;
    final SocketChannel channel;

    BaseGunNettyWorker(final GunPilelineInterface pileline, final SocketChannel channel) {
        this.channel = channel;
        this.pileline = pileline;
    }
}
