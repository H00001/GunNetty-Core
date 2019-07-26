package top.gunplan.netty.impl;

import top.gunplan.netty.*;
import top.gunplan.netty.anno.GunNetFilterOrder;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * GunNettyPipelineImpl
 *
 * @author dosdrtt
 * @see GunNettyPipeline
 */
final class GunNettyPipelineImpl implements GunNettyPipeline {

    private volatile GunNettyHandle handle;
    private final List<GunNettyFilter> filterChain = new CopyOnWriteArrayList<>();
    private final List<GunNettyTimer> timers = new CopyOnWriteArrayList<>();

    @Override
    public GunNettyPipeline register(GunHandle handle) {
        assert handle != null;
        if (handle instanceof GunNettyHandle) {
            setHandle0((GunNettyHandle) handle);
        } else if (handle instanceof GunNettyFilter) {
            addFilter((GunNettyFilter) handle);
        } else if (handle instanceof GunNettyTimer) {
            addTimer((GunNettyTimer) handle);
        }
        return this;
    }

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

    private void setHandle0(GunNettyHandle handle) {
        this.handle = handle;
    }

    @Override
    public GunNettyPipeline addFilter(GunNettyFilter filter) {
        addFilter0(filter);
        return this;
    }

    @Override
    public GunNettyPipeline setHandle(GunNettyHandle handle) {
        if (handle != null) {
            setHandle0(handle);
        }
        return this;
    }


    @Override
    public GunNettyPipeline refSetHandle(Class<? extends GunHandle> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (clazz != null) {
            GunHandle h = clazz.getDeclaredConstructor().newInstance();
            if (h instanceof GunNettyHandle) {
                setHandle0((GunNettyHandle) h);
            } else if (h instanceof GunNettyFilter) {
                addFilter((GunNettyFilter) h);
            }
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
    public List<GunNettyFilter> getFilters() {
        return filterChain;
    }

    @Override
    public GunNettyHandle getHandel() {
        return handle;
    }

    @Override
    public List<GunNettyTimer> getTimer() {
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
