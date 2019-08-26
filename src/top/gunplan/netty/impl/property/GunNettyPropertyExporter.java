/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property;

import top.gunplan.netty.common.GunNettyContext;

/**
 * GunNettyPropertyExporter
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-08-05 08:19
 */
public interface GunNettyPropertyExporter {

    /**
     * export
     *
     * @param k key
     * @param v value
     */
    default void export(String k, String v) {
        GunNettyContext.logger.setTAG(GunNettyPropertyExporter.class).info(k + ":" + v, "[PROPERTY]");
    }


    /**
     * export
     *
     * @param k export k
     */
    default void export(String k) {
        GunNettyContext.logger.setTAG(GunNettyPropertyExporter.class).info(k);
    }
}
