package top.gunplan.netty.impl;

/**
 * GunNettyTranslator
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-29 21:46
 */
@FunctionalInterface
public interface GunNettyTranslator {
    /**
     * translator data
     * from byte[] to object or
     * from object ro byte[]
     */
    void translate();
}
