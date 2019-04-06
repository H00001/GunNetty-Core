package top.gunplan.netty.handles.http;

import top.gunplan.netty.protocol.GunNetInputInterface;
import top.gunplan.netty.protocol.GunNetResponseInterface;

/**
 * @param <T>
 * @author dosdrtt
 */
public interface GunHttpMappingHandle<T extends GunNetResponseInterface> {
    /**
     *
     * @param protocl {@link GunNetInputInterface}
     * @return {@link GunNetResponseInterface}
     */
    T doOutput(GunNetInputInterface protocl);
}

