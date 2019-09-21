/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property.base;

import java.io.IOException;

/**
 * PropertyDataBuilder
 *
 * @author frank albert
 * @version 0.0.0.1
 */
@FunctionalInterface
public interface PropertyDataBuilder<D> {
    /**
     * create data
     *
     * @return data to get properties
     * @throws IOException read error
     */
    D create() throws IOException;
}
