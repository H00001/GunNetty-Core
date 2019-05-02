package top.gunplan.netty.impl;

import top.gunplan.netty.*;
import top.gunplan.netty.anno.GunNetFilterOrder;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class GunPilelineImpl implements GunPilelineInterface {
    private GunNettyHandle handle;
    private List<GunNettyFilter> filterChain = new CopyOnWriteArrayList<>();

    @Override
    public GunPilelineInterface register(GunHandle handle) {
        assert handle != null;
        if (handle instanceof GunNettyHandle) {
            setHandle0((GunNettyHandle) handle);
        } else if (handle instanceof GunNettyFilter) {
            addFilter((GunNettyFilter) handle);
        }

        return this;
    }

    private void addFilter0(GunNettyFilter filter) {
        GunNetFilterOrder order = filter.getClass().getAnnotation(GunNetFilterOrder.class);
        this.filterChain.add(order.index(), filter);
    }

    private void setHandle0(GunNettyHandle handle) {
        this.handle = handle;
    }

    @Override
    public GunPilelineInterface addFilter(GunNettyFilter filter) {
        addFilter0(filter);
        return this;
    }

    @Override
    public GunPilelineInterface setHandle(GunNettyHandle handle) {
        if (handle != null) {
            setHandle0(handle);
        }
        return this;
    }


    @Override
    public GunPilelineInterface refSetHandle(Class<? extends GunHandle> clazz) throws IllegalAccessException, InstantiationException {
        if (clazz != null) {
            GunHandle h = clazz.newInstance();
            if (h instanceof GunNettyHandle) {
                setHandle0((GunNettyHandle) h);
            } else if (h instanceof GunNettyFilter) {
                addFilter((GunNettyFilter) h);
            }
        }
        return this;
    }

    @Override
    public GunPilelineCheckResult check() {
        if (handle != null && filterChain.size() > 0) {
            return new GunPilelineCheckResult(GunPilelineCheckResult.CheckResult.SAFE);
        } else if (handle == null && filterChain.size() > 0) {
            return new GunPilelineCheckResult(GunPilelineCheckResult.CheckResult.UNSAFE);
        } else if (handle != null) {
            return new GunPilelineCheckResult(GunPilelineCheckResult.CheckResult.WARNNING);
        } else {
            return new GunPilelineCheckResult(GunPilelineCheckResult.CheckResult.ERROR, "please set handle and filter");
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

}
