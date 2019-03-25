package top.gunplan.netty.test;


import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.protocol.BaseGunHttp2Response;
import top.gunplan.netty.protocol.GunHttp2ResponseInterface;
import top.gunplan.netty.protocol.GunHttpStdInfo;
import top.gunplan.netty.protocol.GunNetRequestInterface;

@GunHttpmapping(mappingRule = "/*")
public class _404_Not_Found implements GunHttpMappingHandle<GunHttp2ResponseInterface> {

    @Override
    public GunHttp2ResponseInterface doResponse(GunNetRequestInterface protocl) {
        BaseGunHttp2Response response = new BaseGunHttp2Response() {
            @Override
            public String toResponse() {
                return "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "</head>" +
                        "<body>" +
                        "<h1>404 n0t found</h1>" +
                        "" +
                        "</body>" +
                        "</html>";
            }
        };
        response.setIswrite(true);
        response.setProtoclType(GunHttpStdInfo.HttpProtoclType.HTTP2_0);
        response.setContentType(GunHttpStdInfo.ContentType.TEXT_HTML);
        return response;
    }
}
