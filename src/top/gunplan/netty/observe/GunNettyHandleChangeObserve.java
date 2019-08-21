/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.observe;

import top.gunplan.netty.GunNettyChildrenHandle;
import top.gunplan.netty.GunNettyParentHandle;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;

/**
 * GunNettyHandleChangeObserve
 *
 * @author frank albert
 * @version 0.0.0.3
 * @date 2019-08-18 13:31
 */
public interface GunNettyHandleChangeObserve {
    /**
     * on Update Handle ,the method will be called
     *
     * @param handle   new handle
     * @param pipeline pipeline
     */
    void onUpdateHandle(GunNettyChildrenHandle handle, GunNettyChildrenPipeline pipeline);

    /**
     * on Update Handle ,the method will be called
     *
     * @param handle   new handle
     * @param pipeline pipeline
     */
    void onUpdateHandle(GunNettyParentHandle handle, GunNettyChildrenPipeline pipeline);

}
