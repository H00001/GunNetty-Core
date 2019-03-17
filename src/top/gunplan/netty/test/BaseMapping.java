package top.gunplan.netty.test;

import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.protocol.CunBaseHttp2Response;
import top.gunplan.netty.protocol.GunHttp2ResponseInterface;

@GunHttpmapping(mappingRule = "/index.jsp")
public class BaseMapping implements GunHttpMappingHandle<GunHttp2ResponseInterface> {
    public BaseMapping() {

    }

    @Override
    public GunHttp2ResponseInterface doResponse() {
        CunBaseHttp2Response jj= new CunBaseHttp2Response(){

        };
        byte[] b = jj.serizResponse();

            return jj;
       // return (;
    }
}
