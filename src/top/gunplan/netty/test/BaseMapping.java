package top.gunplan.netty.test;

import top.gunplan.netty.anno.GunHttpBaseContent;
import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.protocol.*;

@GunHttpmapping(mappingRule = "/index.aspx")
@GunHttpBaseContent
public class BaseMapping implements GunHttpMappingHandle<GunNetOutputInterface> {
    public BaseMapping() {

    }

    @Override
    public GunNetOutputInterface doOutput(GunNetInputInterface protocl) {
        BaseGunHttp2Response response = new BaseGunHttp2Response() {
            @Override
            public String toResponse() {
                return "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "</head>" +
                        "<body>" +
                        "<h1>this is a web server by GunNetty</h1>" +
                        "<p>get start <a href=\"http://netty.gunplan.top\">download</a></p>" +
                        "</body>" +
                        "</html>";
            }
        };
        response.setIswrite(true);
        response.setProtoclType(GunHttpStdInfo.HttpProtoclType.HTTP1_1);
        response.setContentType(GunHttpStdInfo.ContentType.TEXT_HTML);

        return response;
        // return (;
    }
}
