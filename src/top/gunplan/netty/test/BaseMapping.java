package top.gunplan.netty.test;

import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.handles.http.GunHttpResponse;

@GunHttpmapping(mappingRule = "/index.jsp")
public class BaseMapping implements GunHttpMappingHandle<GunHttpResponse> {
    @Override
    public GunHttpResponse doResponse() {
        return (() -> "succeed");
    }
}
