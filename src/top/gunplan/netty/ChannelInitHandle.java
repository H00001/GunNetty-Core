/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.impl.GunNettyPipeline;

/**
 * ChannelInitHandle
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-08 23:31
 */

@FunctionalInterface
public interface ChannelInitHandle extends GunHandle {
    void onHasChannel(GunNettyPipeline pipeline);
}
