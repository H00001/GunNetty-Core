package top.gunplan.netty.common;


import top.gunplan.netty.impl.propertys.GunCoreProperty;

import java.lang.reflect.Field;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Objects;

/**
 * @author dosdrtt
 */
public class GunNettyProperty {
    private static GunCoreProperty core = null;

    public static GunCoreProperty getCore() {
        return core;
    }

    public static boolean getProperty() {
        byte[] property;
        try {
            property = Files.readAllBytes(Paths.get(Objects.requireNonNull(GunNettyProperty.class.getClassLoader().getResource("GunNetty.conf")).toURI()));
            String[] propertys = new String(property).split("\n");
            Field fd;
            String proname;
            for (int now = 0; now < property.length; now++) {
                if (!propertys[now].startsWith("#")) {
                    if (propertys[now].endsWith(":")) {
                        fd = GunNettyProperty.class.getDeclaredField(propertys[now].replace(":", ""));
                        fd.setAccessible(false);
                        final Object obj = fd.getType().getConstructor().newInstance();
                        fd.set(null, obj);
                        now++;
                        for (; now < property.length; now++) {
                            proname = propertys[now].replace(" ", "");
                            fd = obj.getClass().getDeclaredField(proname.split("=")[0]);
                            fd.setAccessible(true);
                            fd.set(obj, Integer.parseInt(proname.split("=")[1]) );
                        }


                    }
//                    Field fd;
//                    fd = GunNettyProperty.class.getDeclaredField(pro.split("=")[0]);
//                    fd.setAccessible(false);
//                    fd.set(null, Integer.parseInt(pro.split("=")[1]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
