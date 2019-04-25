package top.gunplan.netty;

import java.io.IOException;

/**
 * @author dosdrtt
 */
@FunctionalInterface
public interface GunFunctionMappingInterFace<F, T> {
    /**
     * @param from from
     * @return to
     * @throws IOException exp
     */
    T readBytes(F from) throws IOException;
}
