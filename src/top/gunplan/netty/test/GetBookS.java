package top.gunplan.netty.test;

import top.gunplan.netty.anno.GunHttpmapping;
import top.gunplan.netty.handles.http.GunHttpMappingHandle;
import top.gunplan.netty.protocol.BaseGunHttp2Response;
import top.gunplan.netty.protocol.GunHttpStdInfo;
import top.gunplan.netty.protocol.GunNetInputInterface;
import top.gunplan.netty.protocol.GunNetOutputInterface;
import top.gunplan.netty.protocol.resputil.GunMappingJsonResp;

import java.util.ArrayList;
import java.util.List;

@GunHttpmapping(mappingRule = "/getBooks.gmh")
public class GetBookS implements GunHttpMappingHandle<GunNetOutputInterface> {

    @Override
    public GunNetOutputInterface doOutput(GunNetInputInterface protocl) {
        BaseGunHttp2Response response = new BaseGunHttp2Response() {

            @Override
            public String toResponse() {

                List<GunMappingJsonResp> resps = new ArrayList<>();
                GunMappingJsonResp resp = new GunMappingJsonResp();
                //connection db
                resp.put("Type","1");
                resp.put("content","i love gmh");
                resp.put("title","i love gmh");

                return resp.toTransfer();
           }
        };
        response.setIswrite(true);

        response.setContentType(GunHttpStdInfo.ContentType.TEXT_JSON);

        return response;
    }
}
