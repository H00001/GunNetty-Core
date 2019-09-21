/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;

/**
 * GunNettyParentPipeline
 *
 * @author frank albert
 * @version 0.0.0.2
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
