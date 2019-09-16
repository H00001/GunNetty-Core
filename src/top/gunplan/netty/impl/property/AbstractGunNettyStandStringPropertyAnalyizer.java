/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.property;

import top.gunplan.netty.GunProperty;
import top.gunplan.netty.common.GunNettyStringUtil;

import java.lang.reflect.Field;
import java.util.Map;

import static top.gunplan.utils.GunNumberUtil.isNumber;

/**
 * AbstractGunNettyStandStringProperty Analyizer
 *
 * @author frank albert
 * @version 0.0.0.1
 * # 2019-08-03 18:21
 */
public abstract class AbstractGunNettyStandStringPropertyAnalyizer implements GunNettyPropertyAnalyzer<String, String[]> {
    private final GunNettyPropertyExporter exporter = new GunNettyPropertyExporter() {
    };
    private final static String UN_USEFUL_CHARS = "#";
    private final static String AS_SEGMENT_CHARS = "=";
    private final static String[] OPEN_AND_CLOSE_CHILD_PROPERTIES = {"{", "}"};

    @Override
    public void analyzingProperties(String[] properties, Map<String, GunProperty> propertiesMap) throws NoSuchFieldException, IllegalAccessException {
        String[] proName;
        Field fd;
        for (int now = 0; now < properties.length; now++) {
            if (!properties[now].startsWith(UN_USEFUL_CHARS)) {
                if (properties[now].endsWith(OPEN_AND_CLOSE_CHILD_PROPERTIES[0])) {
                    if (!GunNettyStringUtil.isNotEmpty0(properties[now])) {
                        break;
                    }
                    final String proHead = properties[now].replace(OPEN_AND_CLOSE_CHILD_PROPERTIES[0], "").trim();
                    final GunProperty obj = propertiesMap.get(proHead);
                    now++;
                    for (; now < properties.length; now++) {
                        if (!GunNettyStringUtil.isNotEmpty0(properties[now])) {
                            break;
                        }
                        if (!properties[now].trim().endsWith(OPEN_AND_CLOSE_CHILD_PROPERTIES[1])) {
                            if (!properties[now].startsWith(UN_USEFUL_CHARS)) {
                                proName = properties[now].replace(" ", "").split(AS_SEGMENT_CHARS);
                                fd = obj.getClass().getDeclaredField(proName[0]);
                                exporter.export(proName[0], proName[1].trim());
                                fd.setAccessible(true);
                                fd.set(obj, isNumber(proName[1].trim()) ? Integer.valueOf(proName[1].trim()) : proName[1].trim());
                            }
                        } else {
                            break;
                        }
                    }
                    if (!obj.doRegex()) {
                        throw new GunReadPropertyException("property regex error:" + obj.getClass());
                    }
                }
            }
            if (properties[now].startsWith("+")) {
                nextAnalyze(propertiesMap, properties[now].replace("+", "").trim());
            }
        }


    }

    /**
     * nextAnalyze
     *
     * @param propertiesMap propertiesMap
     * @param info          next information
     */
    @Override
    public abstract void nextAnalyze(Map<String, GunProperty> propertiesMap, String info);
}
