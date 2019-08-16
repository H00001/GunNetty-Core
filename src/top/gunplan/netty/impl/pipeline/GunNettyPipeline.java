/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;


import top.gunplan.netty.*;

import java.util.List;

/**
 * @author dosdrtt
 * @since 1.0.0.1
 */
public interface GunNettyPipeline<KHAND extends GunNettyHandle> extends GunHandle {


    /**
     * addTimer
     *
     * @param timer {@link GunNettyTimer}
     * @return this, chain style
     */
    GunNettyPipeline addTimer(GunNettyTimer timer);

    /**
     * addFilter
     *
     * @param filter {@link GunNettyFilter}
     * @return this, chain style
     */
    GunNettyPipeline addFilter(GunNettyFilter filter);

    /**
     * setHandle
     *
     * @param handle GunNettyHandle
     * @return this.chain style
     */
    GunNettyPipeline setHandle(KHAND handle);

    /**
     * check the pileline model avilable
     *
     * @return check result
     */
    GunPipelineCheckResult check();


    /**
     * filters
     *
     * @return List<GunNettyFilter> GunNettyFilter's List
     */
    List<GunNettyFilter> filters();


    /**
     * handel
     *
     * @return GunNettyHandle
     */
    KHAND handel();


    /**
     * timer
     *
     * @return List<GunNettyTimer>
     */
    List<GunNettyTimer> timer();


    /**
     * init
     * <p>
     * be called at init
     *
     * @return int
     */
    @Override
    int init();

}
