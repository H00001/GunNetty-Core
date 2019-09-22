/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;


import top.gunplan.netty.*;
import top.gunplan.netty.observe.GunNettyHandleChangeObserve;

import java.util.List;

/**
 * @author dosdrtt
 * @since 1.0.0.4
 */
public interface GunNettyPipeline extends GunHandle {
    /**
     * setHandle
     *
     * @param handle GunNettyHandle
     * @return this.chain style
     */
    GunNettyPipeline setHandle(GunNettyChildrenHandle handle);


    /**
     * setHandle
     *
     * @param handle GunNettyHandle
     * @return this.chain style
     */
    GunNettyPipeline setHandle(GunNettyParentHandle handle);


    /**
     * find handle by tag
     * every handle should have a tag
     *
     * @return this.chain style
     */
    <N extends GunNettyHandle> N findHandleByTag(String tag);

    /**
     * check the pipeline model available
     *
     * @return check result
     */
    GunPipelineCheckResult check();

    /**
     * data handel
     *
     * @return GunNettyHandle
     */
    GunNettyChildrenHandle childHandel();


    /**
     * connection handel
     *
     * @return GunNettyHandle
     */
    GunNettyParentHandle parentHandel();

    /**
     * timers
     *
     * @return timer list GunNettyTimer
     */
    List<GunNettyTimer> timers();


    /**
     * init
     * <p>
     * be called at init
     *
     * @return int
     */
    @Override
    int init();


    /**
     * setPipelineHandleChangeObserve
     *
     * @param observe observe
     * @return this chain style
     */
    GunNettyPipeline setPipelineHandleChangeObserve(GunNettyHandleChangeObserve observe);


    /**
     * add child Timer
     *
     * @param timer {@link GunNettyTimer}
     * @return this, chain style
     */
    GunNettyPipeline addNettyTimer(GunNettyTimer timer);


}

