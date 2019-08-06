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
 * @date 2019-08-06 12:58
 */
class NvPropertyConfigureGetterImpl implements NvPropertyConfigureGetter {
    GunNettyPropertyAnalyzer<String, String[]> analyzier;
    Map<String, GunProperty> p;
    private PropertyDataBuilder<String[]> creater = new GunGetPropertyFromBaseFile();

    void doGetProperty() {
        CompletableFuture.supplyAsync(() -> {
            try {
                return creater.create();
            } catch (IOException e) {
                return null;
            }
        }).thenAcceptAsync((v) -> {
            if (v != null) {
                try {
                    analyzier.analyzingProperties(v, p);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                }
            }
        }).join();
    }
}
