/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;

import top.gunplan.netty.GunNettyChildrenHandle;

/**
 * GunNettyChildrenPipeline
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-13 09:35
 */

public interface GunNettyChildrenPipeline extends GunNettyPipeline<GunNettyChildrenHandle> {
    /**
     * newPipeline
     *
     * @return AbstractNettyPipelineImpl
     */
    static GunNettyChildrenPipeline newPipeline() {
        return new GunNettyChildrenPipelineImpl();
    }
}
