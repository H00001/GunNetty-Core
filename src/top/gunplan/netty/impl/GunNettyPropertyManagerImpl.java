package top.gunplan.netty.impl;


import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionTypes;
import top.gunplan.netty.GunProperty;
import top.gunplan.netty.anno.GunPropertyMap;
import top.gunplan.netty.common.GunNettyPropertyManager;
import top.gunplan.netty.impl.propertys.GunGetPropertyFromBaseFile;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.netty.impl.propertys.GunLogProperty;
import top.gunplan.netty.impl.propertys.GunPropertyStrategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.HashMap;
import java.util.Map;


/**
 * analyzing config information by reference
 *
 * @author dosdrtt
 * @date 2019/04/22
 * @see GunNettyPropertyManager
 */

public final class GunNettyPropertyManagerImpl implements GunNettyPropertyManager {

    private static Map<String, GunProperty> propertiesMap = new HashMap<>();
    private static GunPropertyStrategy strategy = new GunGetPropertyFromBaseFile();

    static {
        try {
            Constructor<GunNettyCoreProperty> cons = GunNettyCoreProperty.class.getDeclaredConstructor();
            cons.setAccessible(true);
            registerProperty(cons.newInstance());
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new GunException(GunExceptionTypes.REF, e);
        }
        registerProperty(new GunLogProperty());
    }

    static GunNettyCoreProperty coreProperty() {
        return getProperty(GunNettyCoreProperty.class);
    }

    public static GunLogProperty logProperty() {
        return getProperty(GunLogProperty.class);
    }


    private static void registerProperty(String name, GunProperty property) {
        propertiesMap.put(name, property);
    }

    public static void registerProperty(GunProperty property) {
        GunPropertyMap propertyMap = property.getClass().getAnnotation(GunPropertyMap.class);
        registerProperty(propertyMap.name(), property);
    }


    public static <T extends GunProperty> T getProperty(Class<T> clazz) {
        GunPropertyMap mmap = clazz.getAnnotation(GunPropertyMap.class);
        final GunProperty property = propertiesMap.get(mmap.name());
        return clazz.cast(property);
    }

    public static boolean initProperty() {
        return strategy.settingProperties(propertiesMap);
    }


    @Override
    public void analyzingProperties() {
        strategy.settingProperties(propertiesMap);
    }

    @Override
    public GunNettyPropertyManager setStrategy(GunPropertyStrategy strategy) {
        GunNettyPropertyManagerImpl.strategy = strategy;
        return this;
    }
}
