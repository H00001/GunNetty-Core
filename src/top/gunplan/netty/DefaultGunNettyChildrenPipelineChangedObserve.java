/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.filter.GunNettyFilter;
import top.gunplan.netty.impl.pipeline.GunNettyChildrenPipeline;
import top.gunplan.netty.observe.GunNettyChildrenPipelineChangedObserve;

/**
 * DefaultGunNettyChildrenPipelineChangedObserve
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-29 06:34
 */
public class DefaultGunNettyChildrenPipelineChangedObserve implements GunNettyChildrenPipelineChangedObserve {
    @Override
    public void onUpdateHandle(GunNettyChildrenHandle handle, GunNettyChildrenPipeline pipeline) {
        GunNettyContext.logger.info("child handle has been added:" + handle);
    }

    @Override
    public void onUpdateHandle(GunNettyParentHandle handle, GunNettyChildrenPipeline pipeline) {
        GunNettyContext.logger.info("parent handle has been added:" + handle);
    }

    @Override
    public <V extends GunNettyFilter> void onAddFilter(V filter, GunNettyChildrenPipeline pipeline) {
        GunNettyContext.logger.info("filter has been added:" + filter);
    }

    @Override
    public <V extends GunNettyFilter> void onRemoveFilter(V filter, GunNettyChildrenPipeline pipeline) {
        GunNettyContext.logger.info("filter has been removed:" + filter);
    }
}
