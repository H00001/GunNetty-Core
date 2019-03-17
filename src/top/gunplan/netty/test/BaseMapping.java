package top.gunplan.netty.test;

import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.protocol.CunBaseHttp2Response;
import top.gunplan.netty.protocol.GunHttp2Response;

@GunHttpmapping(mappingRule = "/index.jsp")
public class BaseMapping implements GunHttpMappingHandle<GunHttp2Response> {
    public BaseMapping() {

    }

    @Override
    public GunHttp2Response doResponse() {
        return (new CunBaseHttp2Response() {

        });
    }
}
