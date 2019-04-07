package top.gunplan.netty.common;


import top.gunplan.netty.impl.GunHttpProperty;
import top.gunplan.netty.impl.propertys.GunCoreProperty;
import top.gunplan.netty.impl.propertys.GunLogProperty;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Pattern;


/**
 * analyzing config information by reference
 *
 * @author dosdrtt
 */
public final class GunNettyPropertyManager {
    public static String unusefulchars = "#";
    public static String assignmentchars = "=";
    public static String[] openandclodechildpropertys = {"{", "}"};

    public static GunLogProperty getLog() {
        return log;
    }

    public static GunHttpProperty getHttp() {
        return http;
    }

    private static GunCoreProperty core = null;
    private static GunHttpProperty http = null;
    private static GunLogProperty log = null;

    public static GunCoreProperty getCore() {
        return core;
    }

    /**
     * reference to get propertys
     *
     * @return ture or false to get field
     */
    public static boolean getProperty() {
        try {
            String[] propertys = new String(Files.readAllBytes(Paths.get(Objects.
                    requireNonNull(GunNettyPropertyManager.class.getClassLoader().
                            getResource("GunNetty.conf")).toURI()))).
                    split("\n");
            Field fd;
            String proname[];
            for (int now = 0; now < propertys.length; now++) {
                if (!propertys[now].startsWith(unusefulchars)) {
                    if (propertys[now].endsWith(openandclodechildpropertys[0])) {
                        fd = GunNettyPropertyManager.class.getDeclaredField(propertys[now].replace(openandclodechildpropertys[0], "").trim());
                        fd.setAccessible(false);
                        final Object obj = fd.getType().getConstructor().newInstance();
                        fd.set(null, obj);
                        now++;
                        for (; now < propertys.length; now++) {
                            if (!propertys[now].trim().endsWith(openandclodechildpropertys[1])) {
                                if (!propertys[now].startsWith(unusefulchars)) {
                                    proname = propertys[now].replace(" ", "").split(assignmentchars);
                                    fd = obj.getClass().getDeclaredField(proname[0]);
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

    public static boolean isInteger(String str) {
        if (str.startsWith("0x")) {
            return true;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
