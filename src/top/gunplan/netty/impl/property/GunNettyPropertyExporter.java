/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
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

    default void export(String k, String v) {
        GunNettyContext.logger.setTAG(GunNettyPropertyExporter.class).info(k + ":" + v, "[PROPERTY]");
    }


    default void export(String k) {
        GunNettyContext.logger.setTAG(GunNettyPropertyExporter.class).info(k);
    }
}
