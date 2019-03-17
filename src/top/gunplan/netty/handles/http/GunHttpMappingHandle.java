package top.gunplan.netty.handles.http;

import top.gunplan.netty.protocol.GunHttp2ResponseInterface;

/**
 * @param <T>
 * @author dosdrtt
 */
public interface GunHttpMappingHandle<T extends GunHttp2ResponseInterface> {
    /**
     * @return
     */
    T doResponse();
}

