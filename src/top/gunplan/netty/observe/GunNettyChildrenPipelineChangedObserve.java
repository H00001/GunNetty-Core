/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.observe;

import top.gunplan.netty.filter.GunNettyFilter;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;

/**
 * GunNettyChildrenPipelineChangedObserve
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-18 12:29
 */
public interface GunNettyChildrenPipelineChangedObserve extends GunNettyHandleChangeObserve {

    /**
     * on add filter
     *
     * @param filter   added filter
     * @param pipeline pipeline
     * @param <V>      v
     */
    <V extends GunNettyFilter> void onAddFilter(V filter, GunNettyChildrenPipeline pipeline);

    /**
     * on remove filter
     *
     * @param filter   removed filter
     * @param pipeline pipeline
     * @param <V>      v
     */
    <V extends GunNettyFilter> void onRemoveFilter(V filter, GunNettyChildrenPipeline pipeline);


}
