package top.gunplan.netty.impl.propertys;

import top.gunplan.netty.GunProperty;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.common.GunNettyStringUtil;
import top.gunplan.utils.GunLogger;

import java.lang.reflect.Field;
import java.util.Map;

import static top.gunplan.utils.NumberUtil.isNumber;

/**
 * AbstractGunNettyStandStringPropertyAnalysiser
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-08-03 18:21
 */
public abstract class AbstractGunNettyStandStringPropertyAnalysiser implements GunNettyPropertyAnalyzier {
    private static final GunLogger LOG = GunNettyContext.logger;

    private static String unusefulchars = "#";
    private static String assignmentchars = "=";
    private static String[] openandclodechildproperties = {"{", "}"};

    @Override
    public void analyzingProperties(String[] properties, Map<String, GunProperty> propertiesMap) throws NoSuchFieldException, IllegalAccessException {
        String[] proName;
        Field fd;
        for (int now = 0; now < properties.length; now++) {
            if (!properties[now].startsWith(unusefulchars)) {
                if (properties[now].endsWith(openandclodechildproperties[0])) {
                    if (!GunNettyStringUtil.isEmpty0(properties[now])) {
                        break;
                    }
                    final String proHead = properties[now].replace(openandclodechildproperties[0], "").trim();
                    final GunProperty obj = propertiesMap.get(proHead);
                    now++;
                    for (; now < properties.length; now++) {
                        if (!GunNettyStringUtil.isEmpty0(properties[now])) {
                            break;
                        }
                        if (!properties[now].trim().endsWith(openandclodechildproperties[1])) {
                            if (!properties[now].startsWith(unusefulchars)) {
                                proName = properties[now].replace(" ", "").split(assignmentchars);
                                fd = obj.getClass().getDeclaredField(proName[0]);
                                LOG.info(proName[0] + ":" + proName[1].trim(), "[PROPERTY]");
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
                nextAnalyzing(propertiesMap, properties[now].replace("+", "").trim());
            }
        }


    }

    @Override
    public abstract void nextAnalyzing(Map<String, GunProperty> propertiesMap, String info);
}
