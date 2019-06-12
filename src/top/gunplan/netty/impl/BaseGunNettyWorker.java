package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyPipeline;


/**
 * BaseGunNettyWorker real woker
 *
 * @author frank albert
 */
abstract class BaseGunNettyWorker implements GunNettyWorkerInterface {
    final GunNettyPipeline pipeline;


    BaseGunNettyWorker(final GunNettyPipeline pipeline) {
        this.pipeline = pipeline;

    }
}
