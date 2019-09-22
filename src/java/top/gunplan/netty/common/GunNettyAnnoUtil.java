/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.common;

import top.gunplan.netty.anno.GunHandleTag;

import java.util.List;

/**
 * GunNettyAnnoUtil
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-09-22 08:36
 */
public class GunNettyAnnoUtil {
    public final static FindStrategy<Object, String> NAME_STAGE = (v, oc) -> {
        GunHandleTag tag = v.getClass().getAnnotation(GunHandleTag.class);
        return (tag != null) && tag.name().equals(oc);
    };
    public final static FindStrategy<Object, Integer> ID_STAGE = (v, oc) -> {
        GunHandleTag tag = v.getClass().getAnnotation(GunHandleTag.class);
        return (tag != null) && tag.id() == oc;
    };

    public static <T, R> T findByAny(final List<T> list, R r, FindStrategy<T, R> strategy) {
        for (var val : list) {
            if (strategy.isThis(val, r)) {
                return val;
            }
        }
        return null;
    }

    public static <T> T findByTag(final List<T> list, String name) {
        for (var val : list) {
            if (NAME_STAGE.isThis(val, name)) {
                return val;
            }
        }
        return null;
    }

    public static <T> T findById(final List<T> list, int id) {
        for (var val : list) {
            if (ID_STAGE.isThis(val, id)) {
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
