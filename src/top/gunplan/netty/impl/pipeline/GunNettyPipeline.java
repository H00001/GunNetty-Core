/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;


import top.gunplan.netty.GunHandle;
import top.gunplan.netty.GunNettyHandle;
import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.GunPipelineCheckResult;

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
     * setHandle
     *
     * @param handle GunNettyHandle
     * @return this.chain style
     */
    GunNettyPipeline setHandle(KHAND handle);

    /**
     * check the pipeline model available
     *
     * @return check result
     */
    GunPipelineCheckResult check();


    /**
     * handel
     *
     * @return GunNettyHandle
     */
    KHAND handel();


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

}
