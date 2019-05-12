package top.gunplan.netty.impl;

import top.gunplan.netty.GunPipeline;

/**
 * BaseGunNettyWorker real woker
 *
 * @author frank albert
 */
abstract class BaseGunNettyWorker implements GunNettyWorkerInterface {
    final GunPipeline pileline;

    BaseGunNettyWorker(final GunPipeline pileline) {
        this.pileline = pileline;
    }
}
