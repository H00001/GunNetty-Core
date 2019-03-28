package top.gunplan.netty.handles.http;

import top.gunplan.netty.protocol.AbstractGunHttp2Response;
import top.gunplan.netty.protocol.GunNetRequestInterface;
import top.gunplan.netty.protocol.GunNetResponseInterface;

/**
 * @param <T>
 * @author dosdrtt
 */
public interface GunHttpMappingHandle<T extends GunNetResponseInterface> {
    /**
     *
     * @param protocl {@link GunNetRequestInterface}
     * @return {@link GunNetResponseInterface}
     */
    T doResponse(GunNetRequestInterface protocl);
}

