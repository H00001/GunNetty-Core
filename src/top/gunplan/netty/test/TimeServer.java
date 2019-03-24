package top.gunplan.netty.test;

import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.protocol.BaseGunHttp2Response;
import top.gunplan.netty.protocol.GunHttp2ResponseInterface;
import top.gunplan.netty.protocol.GunNetRequestInterface;

@GunHttpmapping(mappingRule = "/time")
public class TimeServer implements GunHttpMappingHandle<GunHttp2ResponseInterface> {
    public TimeServer() {
    }


    @Override
    public GunHttp2ResponseInterface doResponse(GunNetRequestInterface protocl) {
        BaseGunHttp2Response response = new BaseGunHttp2Response() {
            @Override
            public String toResponse() {
                return "{\"time\":\""+System.currentTimeMillis()+"\"}";
            }
        };
        response.setIswrite(true);
        return response;
    }
}
