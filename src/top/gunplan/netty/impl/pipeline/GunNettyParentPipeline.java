/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;

/**
 * GunNettyParentPipeline
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-13 09:46
 */
public interface GunNettyParentPipeline extends GunNettyPipeline {


    /**
     * create a new instance
     *
     * @return GunNettyParentPipeline
     */
    static GunNettyParentPipeline newPipeline() {
        return new GunNettyParentPipelineImpl();
    }
}
