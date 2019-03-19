package top.gunplan.netty.test;

import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.protocol.BaseGunHttp2Response;
import top.gunplan.netty.protocol.GunHttp2ResponseInterface;
import top.gunplan.netty.protocol.GunNetRequestInterface;

@GunHttpmapping(mappingRule = "/index.jsp")
public class BaseMapping implements GunHttpMappingHandle<GunHttp2ResponseInterface> {
    public BaseMapping() {

    }

    @Override
    public GunHttp2ResponseInterface doResponse(GunNetRequestInterface protocl) {
        BaseGunHttp2Response jj = new BaseGunHttp2Response() {
            @Override
            public String toResponse() {
                return "hello world";
            }
        };
        jj.setIswrite(true);

        return jj;
        // return (;
    }
}
