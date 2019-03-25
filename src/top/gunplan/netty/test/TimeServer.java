package top.gunplan.netty.test;


import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.protocol.BaseGunHttp2Response;
import top.gunplan.netty.protocol.AbstractGunHttp2Response;
import top.gunplan.netty.protocol.GunNetRequestInterface;
import top.gunplan.netty.protocol.resputil.GunMappingJsonResp;

@GunHttpmapping(mappingRule = "/time")
public class TimeServer implements GunHttpMappingHandle<AbstractGunHttp2Response> {
    public TimeServer() {
    }


    @Override
    public AbstractGunHttp2Response doResponse(GunNetRequestInterface protocl) {
        BaseGunHttp2Response response = new BaseGunHttp2Response() {
            @Override
            public String toResponse() {
                GunMappingJsonResp resp = new GunMappingJsonResp();
                resp.put("time", String.valueOf(System.currentTimeMillis()));
                return resp.toTransfer();
            }
        };
        response.setIswrite(true);
        return response;
    }
}
