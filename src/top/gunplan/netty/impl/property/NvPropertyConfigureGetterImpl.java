/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property;

import top.gunplan.netty.GunProperty;
import top.gunplan.netty.impl.property.base.PropertyDataBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * NvPropertyConfigureGetterImpl
 *
 * @author frank albert
 * @version 0.0.0.1
 * # 2019-08-06 12:58
 *
 * @deprecated
 */
@Deprecated
class NvPropertyConfigureGetterImpl implements NvPropertyConfigureGetter {
    GunNettyPropertyAnalyzer<String, String[]> analyser;
    Map<String, GunProperty> p;
    private PropertyDataBuilder<String[]> creator = new GunGetPropertyFromBaseFile();

    void doGetProperty() {
        CompletableFuture.supplyAsync(() -> {
            try {
                return creator.create();
            } catch (IOException e) {
                return null;
            }
        }).thenAcceptAsync((v) -> {
            if (v != null) {
                try {
                    analyser.analyzingProperties(v, p);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                }
            }
        }).join();
    }
}
