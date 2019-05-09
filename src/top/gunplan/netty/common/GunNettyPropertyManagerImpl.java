package top.gunplan.netty.common;


import top.gunplan.netty.impl.propertys.GunCoreProperty;
import top.gunplan.netty.impl.propertys.GunLogProperty;
import top.gunplan.netty.impl.propertys.GunProperty;
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
 */

public final class GunNettyPropertyManagerImpl implements GunNettyPropertyManager {

    private static String unusefulchars = "#";
    private static String assignmentchars = "=";
    private static String[] openandclodechildpropertys = {"{", "}"};
    private static String BASE_CORE = "core";
    private static String BASE_LOG = "log";
    private static Map<String, GunProperty> propertysmap = new HashMap<>();

    static {
        registerProperty(BASE_CORE, new GunCoreProperty());
        registerProperty(BASE_LOG, new GunLogProperty());
    }

    public static GunCoreProperty coreProperty() {
        return getProperty(BASE_CORE);
    }

    public static GunLogProperty logProperty() {
        return getProperty(BASE_LOG);
    }

    public static void registerProperty(String name, GunProperty proPerty) {
        propertysmap.put(name, proPerty);
    }

    public static <T extends GunProperty> T getProperty(/*unchecked */ String name) {
        final GunProperty proPerty = propertysmap.get(name);
        return proPerty != null ? (T) (proPerty) : null;
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
        try {
            String[] propertys = new String(Files.readAllBytes(Paths.get(Objects.
                    requireNonNull(GunNettyPropertyManagerImpl.class.getClassLoader().
                            getResource("GunNetty.conf")).toURI()))).
                    split("\n");
            realAnalyPropertys(propertys);
        } catch (Exception e) {
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
                    final Object obj = propertysmap.get(prohead);
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

                }
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
