package top.gunplan.netty.common;


import top.gunplan.netty.impl.propertys.GunCoreProperty;
import top.gunplan.netty.impl.propertys.GunLogProperty;
import top.gunplan.netty.impl.propertys.GunProPerty;
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
    private static Map<String, GunProPerty> propertysmap = new HashMap<>();

    static {
        registerProperty("core", new GunCoreProperty());
        registerProperty("log", new GunLogProperty());

    }


    public static void registerProperty(String name, GunProPerty proPerty) {
        propertysmap.put(name, proPerty);
    }

    public static <T extends GunProPerty> T getProperty(String name) {
        final GunProPerty proPerty = propertysmap.get(name);
        return proPerty != null ? (T) proPerty : null;
    }

    public static void setUnusefulchars(String unusefulchars) {
        GunNettyPropertyManagerImpl.unusefulchars = unusefulchars;
    }

    public static void setAssignmentchars(String assignmentchars) {
        GunNettyPropertyManagerImpl.assignmentchars = assignmentchars;
    }

    public static void setOpenandclodechildpropertys(String[] openandclodechildpropertys) {
        GunNettyPropertyManagerImpl.openandclodechildpropertys = openandclodechildpropertys;
    }


//    public static GunLogProperty getLog() {
//        return log;
//    }
//
//    public static GunHttpProperty getHttp() {
//        return http;
//    }

//    private static GunCoreProperty core = null;
//    private static GunHttpProperty http = null;
//    private static GunLogProperty log = null;

//    public static GunCoreProperty getCore() {
//        return core;
//    }

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
            Field fd;
            String[] proname;
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
                                    AbstractGunBaseLogUtil.info(proname[0] + ":" + proname[1].trim(),"[PROPERTY]");
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static boolean isInteger(String str) {
        if (str.startsWith("0x")) {
            return true;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
