/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.common;

import top.gunplan.netty.anno.GunHandleTag;
import top.gunplan.netty.filter.GunNettyFilter;

import java.util.List;

/**
 * GunNettyAnnoUtil
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-09-22 08:36
 */
public class GunNettyAnnoUtil {
    private final static FindStrategy<GunNettyFilter, String> NAME_STAGE = (v, oc) -> {
        GunHandleTag tag = v.getClass().getAnnotation(GunHandleTag.class);
        return (tag != null) && tag.name().equals(oc);
    };
    private final static FindStrategy<GunNettyFilter, Integer> ID_STAGE = (v, oc) -> {
        GunHandleTag tag = v.getClass().getAnnotation(GunHandleTag.class);
        return (tag != null) && tag.id() == oc;
    };
    private final static FindStrategy<Object, Class<?>> ClASS_STAGE = (v, oc) -> v.getClass() == oc;

    public static <T, R> T findByAny(final List<T> list, R r, FindStrategy<T, R> strategy) {
        return findGeneral(list, r, strategy);
    }


    public static <T extends GunNettyFilter> T findByTag(final List<T> list, String name) {
        return findGeneral(list, name, NAME_STAGE);
    }

    public static <T extends GunNettyFilter> T findByClass(final List<T> list, Class<T> name) {
        return findGeneral(list, name, ClASS_STAGE);
    }

    public static <T extends GunNettyFilter> T findById(final List<T> list, Integer id) {
        return findGeneral(list, id, ID_STAGE);

    }

    @SuppressWarnings("unchecked")
    private static <T, R, V> T findGeneral(final List<T> list, V id, FindStrategy<R, V> strategy) {
        for (var val : list) {
            if (strategy.isThis((R) val, id)) {
                return val;
            }
        }
        return null;
    }

    @FunctionalInterface
    interface FindStrategy<V, K extends Object> {
        boolean isThis(V v, K oc);
    }
}
