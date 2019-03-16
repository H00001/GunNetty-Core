package top.gunplan.netty.handles.http;

/**
 * @author dosdrtt
 * @param <T>
 */
public interface GunHttpMappingHandle<T extends GunHttpResponse> {
    T doResponse();
}

