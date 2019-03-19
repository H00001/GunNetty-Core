package top.gunplan.netty.handles.http;

import top.gunplan.netty.protocol.GunHttp2ResponseInterface;
import top.gunplan.netty.protocol.GunNetRequestInterface;

/**
 * @param <T>
 * @author dosdrtt
 */
public interface GunHttpMappingHandle<T extends GunHttp2ResponseInterface> {
    /**
     * @return
     */
    T doResponse(GunNetRequestInterface protocl);
}

