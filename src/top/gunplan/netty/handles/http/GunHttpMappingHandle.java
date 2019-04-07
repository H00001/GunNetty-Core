package top.gunplan.netty.handles.http;

import top.gunplan.netty.protocol.GunNetInputInterface;
import top.gunplan.netty.protocol.GunNetOutputInterface;

/**
 * @param <T>
 * @author dosdrtt
 */
public interface GunHttpMappingHandle<T extends GunNetOutputInterface> {
    /**
     *
     * @param protocl {@link GunNetInputInterface}
     * @return {@link GunNetOutputInterface}
     */
    T doOutput(GunNetInputInterface protocl);
}

