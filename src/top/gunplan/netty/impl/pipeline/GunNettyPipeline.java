/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;


import top.gunplan.netty.*;

import java.util.List;

/**
 * @author dosdrtt
 * @since 1.0.0.4
 */
public interface GunNettyPipeline extends GunHandle {
    /**
     * add child Timer
     *
     * @param timer {@link GunNettyTimer}
     * @return this, chain style
     */
    GunNettyPipeline addTimer(GunNettyTimer timer);

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


    void setPipelineHandleChangeObserve(GunNettyHandleChangeObserve observe);

}

