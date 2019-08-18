/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;

import top.gunplan.netty.GunNettyChildrenHandle;
import top.gunplan.netty.GunNettyDataFilter;
import top.gunplan.netty.GunNettyFilter;

import java.util.List;

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


    /**
     * add Filter
     *
     * @param filter {@link GunNettyFilter}
     * @return this, chain style
     */
    GunNettyPipeline addFilter(GunNettyDataFilter filter);


    /**
     * ¬
     * remove Filter
     *
     * @param filter {@link GunNettyDataFilter}
     * @return this, chain style
     */
    GunNettyPipeline removeFilter(GunNettyDataFilter filter);

    /**
     * filters
     *
     * @return List<GunNettyFilter> GunNettyFilter's List
     */
    List<GunNettyDataFilter> filters();
}
