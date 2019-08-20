/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import java.io.IOException;
import java.nio.channels.Channel;

/**
 * @author dosdrtt
 */
@FunctionalInterface
public interface GunFunctionMapping<F extends Channel, T> {
    /**
     * readBytes
     *
     * @param from from
     * @return to
     * @throws IOException exp
     */
    T readBytes(F from) throws IOException;
}
