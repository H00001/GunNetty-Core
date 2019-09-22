/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;

import top.gunplan.netty.*;
import top.gunplan.netty.anno.GunHandleTag;
import top.gunplan.netty.observe.GunNettyHandleChangeObserve;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * AbstractNettyPipelineImpl
 *
 * @author dosdrtt
 * @see GunNettyPipeline
 */
abstract class AbstractNettyPipelineImpl implements GunNettyPipeline {
    private volatile GunNettyChildrenHandle cHandle;
    private GunNettyHandleChangeObserve baseObserve;
    private volatile GunNettyParentHandle pHandle;
    private final List<GunNettyTimer> timers = new CopyOnWriteArrayList<>();

    @Override
    public GunNettyPipeline addNettyTimer(GunNettyTimer timer) {
        if (timer != null) {
            timers.add(timer);
        }
        return this;
    }


    @Override
    public synchronized GunNettyPipeline setPipelineHandleChangeObserve(GunNettyHandleChangeObserve baseObserve) {
        this.baseObserve = baseObserve;
        return this;
    }


    private void setHandle0(GunNettyChildrenHandle handle) {
        this.cHandle = handle;
    }


    private void setHandle0(GunNettyParentHandle handle) {
        this.pHandle = handle;
    }


    @Override
    public GunNettyPipeline setHandle(GunNettyChildrenHandle handle) {
        if (handle != null) {
            baseObserve.onUpdateHandle(handle, (GunNettyChildrenPipeline) this);
            setHandle0(handle);
        }
        return this;
    }


    @Override
    public GunNettyPipeline setHandle(GunNettyParentHandle handle) {
        if (handle != null) {
            baseObserve.onUpdateHandle(handle, (GunNettyChildrenPipeline) this);
            setHandle0(handle);
        }
        return this;
    }


    @Override
    public GunNettyParentHandle parentHandel() {
        return pHandle;
    }

    @Override
    public GunNettyChildrenHandle childHandel() {
        return cHandle;
    }

    @Override
    public List<GunNettyTimer> timers() {
        return timers;
    }


    @Override
    public int destroy() {
        timers.parallelStream().forEach(GunHandle::destroy);
        return cHandle.destroy() | (pHandle != null ? pHandle.init() : 0);
    }

    @Override
    public int init() {
        timers.parallelStream().forEach(GunHandle::init);
        return cHandle.init() | (pHandle != null ? pHandle.init() : 0);
    }


    public static boolean isBelongTag(GunNettyHandle handle, String tag) {
        GunHandleTag tag0;
        return (tag0 = handle.getClass().getAnnotation(GunHandleTag.class)) != null
                && tag0.name().equals(tag);
    }

    @Override
    public <N extends GunNettyHandle> N findHandleByTag(String tag) {
        return isBelongTag(cHandle, tag) ? (N) cHandle : (isBelongTag(pHandle, tag) ? (N) pHandle : null);
    }
}
