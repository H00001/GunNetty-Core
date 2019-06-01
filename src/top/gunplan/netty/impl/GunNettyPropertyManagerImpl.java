package top.gunplan.netty.impl;


import top.gunplan.netty.GunException;
import top.gunplan.netty.anno.GunPropertyMap;
import top.gunplan.netty.common.GunNettyPropertyManager;
import top.gunplan.netty.common.GunNettyStringUtil;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;
import top.gunplan.netty.impl.propertys.GunLogProperty;
import top.gunplan.netty.impl.propertys.GunCoreProperty;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;


/**
 * analyzing config information by reference
 *
 * @author dosdrtt
 * @date 2019/04/22
 * @see GunNettyPropertyManager
 */

public final class GunNettyPropertyManagerImpl implements GunNettyPropertyManager {

    private static String unusefulchars = "#";
    private static String assignmentchars = "=";
    private static String[] openandclodechildpropertys = {"{", "}"};
    private static Map<String, GunCoreProperty> propertysmap = new HashMap<>();

    static {
        registerProperty(new GunNettyCoreProperty());
        registerProperty(new GunLogProperty());
    }

    protected static GunNettyCoreProperty coreProperty() {
        return getProperty(GunNettyCoreProperty.class);
    }

    public static GunLogProperty logProperty() {
        return getProperty(GunLogProperty.class);
    }


    private static void registerProperty(String name, GunCoreProperty property) {
        propertysmap.put(name, property);
    }

    public static void registerProperty(GunCoreProperty property) {
        GunPropertyMap propertyMap = property.getClass().getAnnotation(GunPropertyMap.class);
        registerProperty(propertyMap.name(), property);
    }


    public static <T extends GunCoreProperty> T getProperty(Class<T> clazz) {
        GunPropertyMap mmap = clazz.getAnnotation(GunPropertyMap.class);
        final GunCoreProperty property = propertysmap.get(mmap.name());
        return clazz.cast(property);
    }

    public static void setUnusefulchars(String unusefulchars) {
        if (GunNettyStringUtil.isEmpty0(unusefulchars)) {
            GunNettyPropertyManagerImpl.unusefulchars = unusefulchars;
        }
    }

    public static void setAssignmentchars(String assignmentchars) {
        if (GunNettyStringUtil.isEmpty0(assignmentchars)) {
            GunNettyPropertyManagerImpl.assignmentchars = assignmentchars;
        }
    }

    public static void setOpenandclodechildpropertys(String[] openandclodechildpropertys) {
        if (!GunNettyStringUtil.isEmpty0(openandclodechildpropertys)) {
            GunNettyPropertyManagerImpl.openandclodechildpropertys = openandclodechildpropertys;
        }

    }


    /**
     * reference to get propertys
     *
     * @return ture or false to get field
     */
    public static boolean initProperty() {

        return initRealProperty("GunNetty.conf");
    }

    private static boolean initRealProperty(String filename) {
        try {
            byte[] read = Files.readAllBytes(Paths.get(Objects.
                    requireNonNull(GunNettyPropertyManagerImpl.class.getClassLoader().
                            getResource(filename)).toURI()));
            String[] propertys = new String(read).split("\n");
            realAnalyPropertys(propertys);
        } catch (Exception e) {
            AbstractGunBaseLogUtil.error("Gun property init fail", "[PROPERTY]");
            AbstractGunBaseLogUtil.error(e);
            return false;
        }
        return true;
    }

    private static void realAnalyPropertys(String[] propertys) throws NoSuchFieldException, IllegalAccessException {
        String[] proname;
        Field fd;
        for (int now = 0; now < propertys.length; now++) {
            if (!propertys[now].startsWith(unusefulchars)) {
                if (propertys[now].endsWith(openandclodechildpropertys[0])) {
                    final String prohead = propertys[now].replace(openandclodechildpropertys[0], "").trim();
                    final GunCoreProperty obj = propertysmap.get(prohead);
                    now++;
                    for (; now < propertys.length; now++) {
                        if (!propertys[now].trim().endsWith(openandclodechildpropertys[1])) {
                            if (!propertys[now].startsWith(unusefulchars)) {
                                proname = propertys[now].replace(" ", "").split(assignmentchars);
                                fd = obj.getClass().getDeclaredField(proname[0]);
                                AbstractGunBaseLogUtil.info(proname[0] + ":" + proname[1].trim(), "[PROPERTY]");
                                fd.setAccessible(true);
                                fd.set(obj, isInteger(proname[1].trim()) ? Integer.valueOf(proname[1].trim()) : proname[1].trim());
                            }
                        } else {
                            break;
                        }
                    }
                    if (!obj.doRegex()) {
                        throw new GunException("property regex error:" + obj.getClass());
                    }
                }
            }
            if (propertys[now].startsWith("+")) {
                initRealProperty(propertys[now].replace("+", "").trim());
            }
        }
    }

    private static boolean isInteger(String str) {
        if (str.startsWith("0x")) {
            return true;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
