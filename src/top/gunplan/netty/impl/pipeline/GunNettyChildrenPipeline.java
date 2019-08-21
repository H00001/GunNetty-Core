/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;

import top.gunplan.netty.filter.GunNettyConnFilter;
import top.gunplan.netty.filter.GunNettyDataFilter;
import top.gunplan.netty.observe.GunNettyChildrenPipelineChangedObserve;

import java.util.List;
import java.util.stream.Stream;

/**
 * GunNettyChildrenPipeline
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-13 09:35
 */

public interface GunNettyChildrenPipeline extends GunNettyPipeline {
    static GunNettyChildrenPipeline newPipeline() {
        return new GunNettyChildrenPipelineImpl();
    }

    /**
     * add data Filter
     *
     * @param filter {@link GunNettyDataFilter}
     * @return this, chain style
     */
    GunNettyChildrenPipeline addDataFilter(GunNettyDataFilter filter);

    /**
     * remove data Filter
     *
     * @param filter {@link GunNettyDataFilter} to remove
     * @return this, chain style
     */
    GunNettyChildrenPipeline removeDataFilter(GunNettyDataFilter filter);

    /**
     * get data filters
     * {@link GunNettyDataFilter}
     *
     * @return List<GunNettyFilter> GunNettyFilter's List
     */
    Stream<GunNettyDataFilter> dataFilterStream();


    /**
     * get data filters
     * {@link GunNettyDataFilter}
     *
     * @return List<GunNettyFilter> GunNettyFilter's List
     */
    List<GunNettyConnFilter> connFilters();


    /**
     * get data filters
     * {@link GunNettyDataFilter}
     *
     * @return List<GunNettyFilter> GunNettyFilter's List
     */
    List<GunNettyDataFilter> dataFilters();

    /**
     * remove connection filter
     *
     * @param filter {@link GunNettyConnFilter} to remove
     * @return List<GunNettyFilter> GunNettyFilter's List
     */
    GunNettyChildrenPipeline removeConnFilter(GunNettyConnFilter filter);

    /**
     * add connection filter
     *
     * @return List<GunNettyFilter> GunNettyFilter's List
     */
    GunNettyChildrenPipeline addConnFilter(GunNettyConnFilter filter);

    /**
     * get connection filters
     *
     * @return List<GunNettyFilter> GunNettyFilter's List
     */
    Stream<GunNettyConnFilter> connFilterStream();


    GunNettyChildrenPipeline setMetaInfoChangeObserver(GunNettyChildrenPipelineChangedObserve observe);
}
