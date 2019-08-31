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
public interface GunNettyPipeline<G extends GunNettyTimer> extends GunHandle {
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
     * @return List<GunNettyTimer>
     */
    List<G> timers();


    /**
     * init
     * <p>
     * be called at init
     *
     * @return int
     */
    @Override
    int init();


    GunNettyPipeline<G> setPipelineHandleChangeObserve(GunNettyHandleChangeObserve observe);


    /**
     * add child Timer
     *
     * @param timer {@link GunNettyTimer}
     * @return this, chain style
     */
    GunNettyPipeline<G> addNettyTimer(G timer);


}

