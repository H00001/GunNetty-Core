/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;

import top.gunplan.netty.*;
import top.gunplan.netty.anno.GunNetFilterOrder;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * AbstractNettyPipelineImpl
 *
 * @author dosdrtt
 * @see GunNettyPipeline
 */
abstract class AbstractNettyPipelineImpl<KHAND extends GunNettyHandle> implements GunNettyPipeline<KHAND> {
    private volatile KHAND handle;
    private final List<GunNettyFilter> filterChain = new CopyOnWriteArrayList<>();
    private final List<GunNettyTimer> timers = new CopyOnWriteArrayList<>();


    @Override
    public GunNettyPipeline addTimer(GunNettyTimer timer) {
        if (timer != null) {
            timers.add(timer);
        }
        return this;
    }


    private void addFilter0(GunNettyFilter filter) {
        GunNetFilterOrder order = filter.getClass().getAnnotation(GunNetFilterOrder.class);
        if (order == null) {
            throw new GunBootServerBase.GunNettyCanNotBootException(new NullPointerException("not have order"));
        }
        this.filterChain.add(order.index(), filter);
    }

    private void setHandle0(KHAND handle) {
        this.handle = handle;
    }

    @Override
    public GunNettyPipeline addFilter(GunNettyFilter filter) {
        addFilter0(filter);
        return this;
    }

    @Override
    public GunNettyPipeline setHandle(KHAND handle) {
        if (handle != null) {
            setHandle0(handle);
        }
        return this;
    }


    @Override
    public GunPipelineCheckResult check() {
        if (handle != null && filterChain.size() > 0) {
            return new GunPipelineCheckResult(GunPipelineCheckResult.CheckResult.SAFE);
        } else if (handle == null && filterChain.size() > 0) {
            return new GunPipelineCheckResult(GunPipelineCheckResult.CheckResult.UNSAFE);
        } else if (handle != null) {
            return new GunPipelineCheckResult(GunPipelineCheckResult.CheckResult.WARNING);
        } else {
            return new GunPipelineCheckResult(GunPipelineCheckResult.CheckResult.ERROR, "please set handle and filter");
        }
    }

    @Override
    public List<GunNettyFilter> filters() {
        return filterChain;
    }

    @Override
    public KHAND handel() {
        return handle;
    }

    @Override
    public List<GunNettyTimer> timer() {
        return timers;
    }


    @Override
    public int destroy() {
        filterChain.parallelStream().forEach(GunHandle::destroy);
        timers.parallelStream().forEach(GunHandle::destroy);
        handle.destroy();
        return 0;
    }

    @Override
    public int init() {
        filterChain.parallelStream().forEach(GunHandle::init);
        timers.parallelStream().forEach(GunHandle::init);
        handle.init();
        return 0;
    }
}
