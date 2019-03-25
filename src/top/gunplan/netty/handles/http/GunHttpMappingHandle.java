package top.gunplan.netty.handles.http;

import top.gunplan.netty.protocol.AbstractGunHttp2Response;
import top.gunplan.netty.protocol.GunNetRequestInterface;

/**
 * @param <T>
 * @author dosdrtt
 */
public interface GunHttpMappingHandle<T extends AbstractGunHttp2Response> {
    /**
     * @return
     */
    T doResponse(GunNetRequestInterface protocl);
}

