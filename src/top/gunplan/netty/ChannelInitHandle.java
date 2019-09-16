/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;

/**
 * ChannelInitHandle
 *
 * @author frank albert
 */

@FunctionalInterface
public interface ChannelInitHandle extends GunHandle {
    /**
     * on have a channel connect
     *
     * @param pipeline base of pipeline
     */
    void onHasChannel(GunNettyChildrenPipeline pipeline);
}
