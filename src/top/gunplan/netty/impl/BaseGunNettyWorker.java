package top.gunplan.netty.impl;

import top.gunplan.netty.GunPileline;

abstract class BaseGunNettyWorker implements GunNettyWorkerInterface {
    final GunPileline pileline;

    BaseGunNettyWorker(final GunPileline pileline) {
        this.pileline = pileline;
    }
}
