package top.gunplan.netty.impl.propertys;

import top.gunplan.netty.GunProperty;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.common.GunNettyStringUtil;

import top.gunplan.utils.GunLogger;

import java.lang.reflect.Field;
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


    private boolean settingProperties0(Map<String, GunProperty> propertyMap, String filename) {
        try {
            byte[] read = Files.readAllBytes(Paths.get(Objects.
                    requireNonNull(this.getClass().getClassLoader().
                            getResource(filename)).toURI()));
            String[] properties = new String(read).split("\n");
            realAnalyPropertys(properties, propertyMap);
        } catch (Exception e) {
            LOG.error("Gun property init fail", "[PROPERTY]");
            LOG.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean settingProperties(Map<String, GunProperty> propertyMap) {
        return settingProperties0(propertyMap, "GunNetty.conf");
    }

    private void realAnalyPropertys(String[] properties, Map<String, GunProperty> propertiesMap) throws NoSuchFieldException, IllegalAccessException {
        String[] proname;
        Field fd;
        for (int now = 0; now < properties.length; now++) {
            if (!properties[now].startsWith(unusefulchars)) {
                if (properties[now].endsWith(openandclodechildproperties[0])) {
                    final String prohead = properties[now].replace(openandclodechildproperties[0], "").trim();
                    final GunProperty obj = propertiesMap.get(prohead);
                    now++;
                    for (; now < properties.length; now++) {
                        if (!properties[now].trim().endsWith(openandclodechildproperties[1])) {
                            if (!properties[now].startsWith(unusefulchars)) {
                                proname = properties[now].replace(" ", "").split(assignmentchars);
                                fd = obj.getClass().getDeclaredField(proname[0]);
                                LOG.info(proname[0] + ":" + proname[1].trim(), "[PROPERTY]");
                                fd.setAccessible(true);
                                fd.set(obj, isNumber(proname[1].trim()) ? Integer.valueOf(proname[1].trim()) : proname[1].trim());
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
