package top.gunplan.netty.impl.propertys;

import top.gunplan.netty.GunBootServerBase;
import top.gunplan.netty.GunProperty;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.common.GunNettyStringUtil;
import top.gunplan.utils.GunLogger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

import static top.gunplan.utils.NumberUtil.isNumber;

/**
 * GunGetPropertyFromBaseFile
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-25 20:55
 */
public class GunGetPropertyFromBaseFile implements GunPropertyStrategy {
    private static final GunLogger LOG = GunNettyContext.logger;
    private static String unusefulchars = "#";
    private static String assignmentchars = "=";
    private static String[] openandclodechildproperties = {"{", "}"};

    public static void setUnusefulchars(String unusefulchars) {
        if (GunNettyStringUtil.isEmpty0(unusefulchars)) {
            GunGetPropertyFromBaseFile.unusefulchars = unusefulchars;
        }
    }

    public static void setAssignmentchars(String assignmentchars) {
        if (GunNettyStringUtil.isEmpty0(assignmentchars)) {
            GunGetPropertyFromBaseFile.assignmentchars = assignmentchars;
        }
    }

    public static void setOpenandclodechildproperties(String[] openandclodechildproperties) {
        if (!GunNettyStringUtil.isEmpty0(openandclodechildproperties)) {
            GunGetPropertyFromBaseFile.openandclodechildproperties = openandclodechildproperties;
        }

    }


    private boolean settingProperties0(Map<String, GunProperty> propertyMap, String filename) throws GunBootServerBase.GunNettyCanNotBootException {
        try {
            String read = Files.readString(Paths.get(Objects.
                    requireNonNull(this.getClass().getClassLoader().getResource(filename)).toURI()));
            String[] properties = read.split("\n");
            realAnalyzingProperties(properties, propertyMap);
        } catch (NoSuchFieldException | IllegalAccessException | IOException | URISyntaxException e) {
            throw new GunBootServerBase.GunNettyCanNotBootException(e);
        }
        return true;
    }

    @Override
    public boolean settingProperties(Map<String, GunProperty> propertyMap) throws GunBootServerBase.GunNettyCanNotBootException {
        return settingProperties0(propertyMap, "GunNetty.conf");
    }

    private void realAnalyzingProperties(String[] properties, Map<String, GunProperty> propertiesMap) throws NoSuchFieldException, IllegalAccessException {
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
                settingProperties0(propertiesMap, properties[now].replace("+", "").trim());
            }
        }
    }


}
