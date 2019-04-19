package top.gunplan.netty;

import java.io.IOException;

/**
 * @author dosdrtt
 */
@FunctionalInterface
public interface GunFUnctionMappingInterFace<F, T> {
    /**
     * @param from
     * @return
     * @throws IOException
     */
    T readBytes(F from) throws IOException;
}
