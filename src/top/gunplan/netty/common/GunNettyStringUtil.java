package top.gunplan.netty.common;

/**
 * @author dosdrtt
 */
final class GunNettyStringUtil {
    static boolean isEmpty0(String in) {
        return in != null && in.trim().length() != 0;
    }

    static boolean isEmpty0(String[] in) {
        return in == null || in.length == 0;
    }
}
