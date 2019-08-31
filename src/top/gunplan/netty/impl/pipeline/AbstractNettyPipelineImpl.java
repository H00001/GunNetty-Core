/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;

import top.gunplan.netty.GunHandle;
import top.gunplan.netty.GunNettyChildrenHandle;
import top.gunplan.netty.GunNettyParentHandle;
import top.gunplan.netty.GunNettyTimer;
import top.gunplan.netty.observe.GunNettyHandleChangeObserve;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * AbstractNettyPipelineImpl
 *
 * @author dosdrtt
 * @see GunNettyPipeline
 */
abstract class AbstractNettyPipelineImpl<G extends GunNettyTimer> implements GunNettyPipeline {
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
        return cHandle.destroy() | pHandle.destroy();
    }

    @Override
    public int init() {
        timers.parallelStream().forEach(GunHandle::init);
        return cHandle.init() | pHandle.init();
    }
}
