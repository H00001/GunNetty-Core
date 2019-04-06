package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyHandle;
import top.gunplan.netty.GunPilelineInterface;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

abstract class BaseGunNettyWorker implements GunNettyWorkerInterface {
    final GunPilelineInterface pileline;

    BaseGunNettyWorker(final GunPilelineInterface pileline) {
        this.pileline = pileline;
    }
}
