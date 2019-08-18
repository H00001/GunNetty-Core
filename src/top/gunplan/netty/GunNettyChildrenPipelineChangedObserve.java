/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;

/**
 * GunNettyChildrenPipelineChangedObserve
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-18 12:29
 */
public interface GunNettyChildrenPipelineChangedObserve extends GunNettyHandleChangeObserve {

    <V extends GunNettyFilter> void onAddFilter(V filter, GunNettyChildrenPipeline pipeline);


    <V extends GunNettyFilter> void onRemoveFilter(V filter, GunNettyChildrenPipeline pipeline);


}
