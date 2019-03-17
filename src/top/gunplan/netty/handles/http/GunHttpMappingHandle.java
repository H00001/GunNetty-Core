package top.gunplan.netty.handles.http;

import top.gunplan.netty.protocol.GunHttp2Response;

/**
 * @param <T>
 * @author dosdrtt
 */
public interface GunHttpMappingHandle<T extends GunHttp2Response> {
    /**
     * @return
     */
    T doResponse();
}

