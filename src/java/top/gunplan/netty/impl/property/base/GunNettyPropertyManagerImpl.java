/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property.base;


import top.gunplan.netty.GunBootServerBase;
import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionMode;
import top.gunplan.netty.GunProperty;
import top.gunplan.netty.anno.GunPropertyMap;
import top.gunplan.netty.impl.property.GunGetPropertyFromBaseFile;
import top.gunplan.netty.impl.property.GunLogProperty;
import top.gunplan.netty.impl.property.GunNettyCoreProperty;
import top.gunplan.netty.impl.property.GunPropertyStrategy;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;


/**
 * analyzing config information by reference
 *
 * @author dosdrtt
 * # 2019/04/22
 * @see GunNettyPropertyManager
 */
final class GunNettyPropertyManagerImpl implements GunNettyPropertyManager {

    private Map<String, GunProperty> propertiesMap = new HashMap<>();
    private GunPropertyStrategy strategy = new GunGetPropertyFromBaseFile();

    GunNettyPropertyManagerImpl() {
        try {
            Constructor<GunNettyCoreProperty> cons = GunNettyCoreProperty.
                    class.getDeclaredConstructor();
            cons.setAccessible(true);
            registerProperty(cons.newInstance());
        } catch (ReflectiveOperationException e) {
            throw new GunException(GunExceptionMode.REF, e);
        }
        registerProperty(new GunLogProperty());
    }


    private void registerProperty(String name, GunProperty property) {
        propertiesMap.put(name, property);
    }

    @Override
    public void registerProperty(GunProperty property) {
        GunPropertyMap propertyMap = property.getClass().
                getAnnotation(GunPropertyMap.class);
        registerProperty(propertyMap.name(), property);
    }


    @Override
    public <T extends GunProperty> T acquireProperty(Class<T> clazz) {
        GunPropertyMap mmap = clazz.getAnnotation(GunPropertyMap.class);
        final GunProperty property = propertiesMap.get(mmap.name());
        return clazz.cast(property);
    }

    @Override
    public boolean initProperty() throws GunBootServerBase.
            GunNettyCanNotBootException {
        return strategy.settingProperties(propertiesMap);
    }


    @Override
    public void analyzingProperties() {
        strategy.settingProperties(propertiesMap);
    }

    @Override
    public GunNettyPropertyManager setStrategy(GunPropertyStrategy strategy) {
        this.strategy = strategy;
        return this;
    }
}
