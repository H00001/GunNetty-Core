/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.pipeline;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionMode;
import top.gunplan.netty.GunPipelineCheckResult;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.common.GunNettyAnnoUtil;
import top.gunplan.netty.filter.GunNettyConnFilter;
import top.gunplan.netty.filter.GunNettyDataFilter;
import top.gunplan.netty.filter.GunNettyFilter;
import top.gunplan.netty.observe.DefaultGunNettyChildrenPipelineChangedObserve;
import top.gunplan.netty.observe.GunNettyChildrenPipelineChangedObserve;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * GunNettyChildrenPipelineImpl
 *
 * @author frank albert
 * @version 0.0.0.1
 * # 2019-08-13 15:02
 */
final class GunNettyChildrenPipelineImpl extends AbstractNettyPipelineImpl
        implements GunNettyChildrenPipeline {
    private final List<GunNettyDataFilter> dataFilters = new CopyOnWriteArrayList<>();
    private final List<GunNettyConnFilter> connFilters = new CopyOnWriteArrayList<>();
    private volatile GunNettyChildrenPipelineChangedObserve observe;

    GunNettyChildrenPipelineImpl() {
        observe = new DefaultGunNettyChildrenPipelineChangedObserve();
        super.setPipelineHandleChangeObserve(observe);
    }

    @Override
    public GunNettyChildrenPipeline addDataFilter(GunNettyDataFilter filter) {
        addFilter0(filter, dataFilters);
        return this;
    }

    @Override
    public GunNettyChildrenPipeline removeDataFilter(GunNettyDataFilter filter) {
        removeFilter0(filter, dataFilters);
        return this;
    }

    @Override
    public Stream<GunNettyDataFilter> dataFilterStream() {
        return dataFilters.stream();
    }

    @Override
    public List<GunNettyConnFilter> connFilters() {
        return connFilters;
    }

    @Override
    public List<GunNettyDataFilter> dataFilters() {
        return dataFilters;
    }

    @Override
    public GunNettyChildrenPipeline removeConnFilter(GunNettyConnFilter filter) {
        removeFilter0(filter, connFilters);
        return this;
    }

    @Override
    public GunNettyChildrenPipeline addConnFilter(GunNettyConnFilter filter) {
        addFilter0(filter, connFilters);
        return this;
    }


    @Override
    public Stream<GunNettyConnFilter> connFilterStream() {
        return connFilters.stream();
    }

    @Override
    public synchronized GunNettyChildrenPipeline setMetaInfoChangeObserver(GunNettyChildrenPipelineChangedObserve observe) {
        if (this.observe != null) {
            this.observe = observe;
            super.setPipelineHandleChangeObserve(observe);
        } else {
            throw new GunException(GunExceptionMode.NULLPTR, "doReset GunNettyChildrenPipelineChangedObserve");
        }
        return this;
    }

    private <U extends GunNettyFilter> void addFilter0(U filter, List<U> list) {
        assert observe != null;
        if (filter != null) {
            GunNetFilterOrder order = filter.getClass().getAnnotation(GunNetFilterOrder.class);
            if (order == null) {
                list.add(filter);
            } else {
                list.add(order.index(), filter);
            }
        }
        observe.onAddFilter(filter, this);
    }


    private <U extends GunNettyFilter> void removeFilter0(U filter, List<U> list) {
        observe.onRemoveFilter(filter, this);
        if (filter != null) {
            list.remove(filter);
        }
    }


    @Override
    public GunPipelineCheckResult check() {
        if (dataFilters.size() > 0) {
            return new GunPipelineCheckResult(GunPipelineCheckResult.CheckResult.SAFE);
        } else if (childHandel() == null && dataFilters.size() > 0) {
            return new GunPipelineCheckResult(GunPipelineCheckResult.CheckResult.UNSAFE);
        } else if (childHandel() != null) {
            return new GunPipelineCheckResult(GunPipelineCheckResult.CheckResult.WARNING);
        } else {
            return new GunPipelineCheckResult(GunPipelineCheckResult.CheckResult.ERROR, "please set handle and filter");
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("chain:");
        for (GunNettyFilter f : dataFilters) {
            builder.append(f.getClass().getSimpleName()).append("->");
        }
        builder.append(childHandel().getClass().getSimpleName()).append("->");
        for (int i = dataFilters.size() - 1; i >= 0; i--) {
            builder.append(dataFilters.get(i).getClass().getSimpleName()).append("->");
        }
        return builder.toString();
    }


    @Override
    public GunNettyDataFilter findDataFilterByTag(String tag) {
        return GunNettyAnnoUtil.findByTag(dataFilters, tag);
    }

    @Override
    public GunNettyConnFilter findConnFilterByTag(String tag) {
        return GunNettyAnnoUtil.findByTag(connFilters, tag);
    }
}
