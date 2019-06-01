package top.gunplan.netty.common;

/**
 * @author dosdrtt
 */
public final class GunNettyStringUtil {
    public static boolean isEmpty0(String in) {
        return in != null && in.trim().length() != 0;
    }

    public static boolean isEmpty0(String[] in) {
        return in == null || in.length == 0;
    }
}
