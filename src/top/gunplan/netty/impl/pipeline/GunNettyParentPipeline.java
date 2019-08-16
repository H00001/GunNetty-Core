/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;

import top.gunplan.netty.GunNettyParentHandle;

/**
 * GunNettyParentPipeline
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-13 09:46
 */
public interface GunNettyParentPipeline extends GunNettyPipeline<GunNettyParentHandle> {
    /**
     * new a new instance
     *
     * @return new instance
     */
    static GunNettyParentPipeline newPipeline() {
        return new GunNettyParentPipelineImpl();
    }
}
