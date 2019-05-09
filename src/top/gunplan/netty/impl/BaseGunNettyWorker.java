package top.gunplan.netty.impl;

import top.gunplan.netty.GunPipeline;

abstract class BaseGunNettyWorker implements GunNettyWorkerInterface {
    final GunPipeline pileline;

    BaseGunNettyWorker(final GunPipeline pileline) {
        this.pileline = pileline;
    }
}
