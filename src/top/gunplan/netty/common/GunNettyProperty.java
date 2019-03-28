package top.gunplan.netty.common;


import java.lang.reflect.Field;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author dosdrtt
 */
public class GunNettyProperty {
    private volatile static int port;
    private volatile static int maxRunnningNum;
    private volatile static int clientWaitTime;
    private volatile static int fileReadBufferMin;

    public static int getFileReadBufferMin() {
        return fileReadBufferMin;
    }

    public static int getClientWaitTime() {
        return clientWaitTime;
    }

    public static int getPort() {
        return port;
    }

    public static int getMaxRunnningNum() {
        return maxRunnningNum;
    }


//    {
//        byte[] property = Files.readAllBytes(Paths.get(GunNettyProperty.class.getClassLoader().getResource("GunNetty.conf").toURI()));
//        String[] propertys = new String(property).split("\n");
//        for (String pro : propertys) {
//            if (!pro.startsWith("#")) {
//                Field fd = GunNettyProperty.class.getDeclaredField(pro.split("=")[0]);
//                fd.setAccessible(false);
//                fd.set(null, Integer.parseInt(pro.split("=")[1]));
//            }
//        }
//    }

    public static boolean getProperty() {
        byte[] property;
        try {
            property = Files.readAllBytes(Paths.get(Objects.requireNonNull(GunNettyProperty.class.getClassLoader().getResource("GunNetty.conf")).toURI()));
            String[] propertys = new String(property).split("\n");
            for (String pro : propertys) {
                if (!pro.startsWith("#")) {
                    Field fd;

                    fd = GunNettyProperty.class.getDeclaredField(pro.split("=")[0]);
                    fd.setAccessible(false);
                    fd.set(null, Integer.parseInt(pro.split("=")[1]));
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
