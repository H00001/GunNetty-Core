package top.gunplan.netty.test;

import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.protocol.BaseGunHttp2Response;
import top.gunplan.netty.protocol.GunHttp2ResponseInterface;
import top.gunplan.netty.protocol.GunHttpStdInfo;
import top.gunplan.netty.protocol.GunNetRequestInterface;
@GunHttpmapping(mappingRule = "/manage/*")
public class ForbiddenMapping implements GunHttpMappingHandle<GunHttp2ResponseInterface> {

    @Override
    public GunHttp2ResponseInterface doResponse(GunNetRequestInterface protocl) {
        BaseGunHttp2Response response = new BaseGunHttp2Response() {

            @Override
            public String toResponse() {
                return "403";
            }
        };

        response.setIswrite(true);
        response.setCode(GunHttpStdInfo.statusCode.FORBIDDEN);
        response.setProtoclType(GunHttpStdInfo.HttpProtoclType.HTTP2_0);
        response.setContentType(GunHttpStdInfo.ContentType.TEXT_HTML);

        return response;
        // return (;
    }

}
