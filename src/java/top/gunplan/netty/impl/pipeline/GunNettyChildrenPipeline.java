/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;

import top.gunplan.netty.GunNettyTimer;
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
 * # 2019-08-13 09:35
 */

public interface GunNettyChildrenPipeline extends GunNettyPipeline {
    /**
     * create a new pipeline
     *
     * @return GunNettyChildrenPipeline
     */
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
     * find DataFilter by tag
     *
     * @param tag tag
     * @return child pipeline GunNettyChildrenPipeline
     */
    GunNettyDataFilter findDataFilterByTag(String tag);


    /**
     * find ConnFilter by tag
     *
     * @param tag tag
     * @return child pipeline GunNettyChildrenPipeline
     */
    GunNettyConnFilter findConnFilterByTag(String tag);

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
     * @return stream  GunNettyFilter's stream
     */
    Stream<GunNettyDataFilter> dataFilterStream();


    /**
     * get data filters
     * {@link GunNettyDataFilter}
     *
     * @return GunNettyFilter's List
     */
    List<GunNettyConnFilter> connFilters();


    /**
     * get data filters
     * {@link GunNettyDataFilter}
     *
     * @return GunNettyFilter's List
     */
    List<GunNettyDataFilter> dataFilters();

    /**
     * remove connection filter
     *
     * @param filter {@link GunNettyConnFilter} to remove
     * @return this chain style
     */
    GunNettyChildrenPipeline removeConnFilter(GunNettyConnFilter filter);

    /**
     * add connection filter
     *
     * @param filter added filter
     * @return this chain style
     */
    GunNettyChildrenPipeline addConnFilter(GunNettyConnFilter filter);

    /**
     * add connection filter
     *
     * @param timer added timer
     * @return GunNettyPipeline chain style
     */
    @Override
    GunNettyPipeline addNettyTimer(GunNettyTimer timer);

    /**
     * get connection filters
     *
     * @return GunNettyFilter's Stream
     */
    Stream<GunNettyConnFilter> connFilterStream();


    /**
     * setMetaInfoChangeObserver
     *
     * @param observe {@link GunNettyChildrenPipelineChangedObserve}
     * @return self chain style
     */
    GunNettyChildrenPipeline setMetaInfoChangeObserver(GunNettyChildrenPipelineChangedObserve observe);
}
