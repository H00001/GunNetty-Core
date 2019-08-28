/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.common;

/**
 * @author dosdrtt
 */
public final class GunNettyStringUtil {
    public static final String EMPTY = "";

    public static boolean isEmpty0(String in) {
        return in != null && in.trim().length() != 0;
    }

    public static boolean isEmpty(String in) {
        return !isEmpty0(in);
    }

    public static boolean isEmpty0(String[] in) {
        return in == null || in.length == 0;
    }
}
