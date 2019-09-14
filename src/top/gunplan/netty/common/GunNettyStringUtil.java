/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.common;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author dosdrtt
 */
public final class GunNettyStringUtil {
    public static final String EMPTY = "";

    public static boolean isNotEmpty0(String in) {
        return in != null && in.trim().length() != 0;
    }

    public static boolean isEmpty(String in) {
        return !isNotEmpty0(in);
    }

    public static boolean isNotEmpty0(String[] in) {
        return in == null || in.length == 0;
    }

    public static String confRead(String fileName) throws URISyntaxException, IOException {
        return Files.readString(Paths.get(ClassLoader.getSystemResource(fileName).toURI()));
    }
}
