package top.gunplan.netty.common;

/**
 * @author dosdrtt
 */
public final class GunNettyStringUtil {
    static boolean isEmpty0(String in) {
        return in != null && !in.equals("") && !in.trim().equals("");
    }

    static boolean isEmpty0(String[] in) {
        return in == null || in.length == 0;
    }
}
