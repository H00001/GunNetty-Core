/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;

/**
 * GunNettyHandleChangeObserve
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-18 13:31
 */
public interface GunNettyHandleChangeObserve {
    void onUpdateHandle(GunNettyChildrenHandle handle, GunNettyChildrenPipeline pipeline);

    void onUpdateHandle(GunNettyParentHandle handle, GunNettyChildrenPipeline pipeline);

}
