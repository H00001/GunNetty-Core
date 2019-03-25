package top.gunplan.netty.protocol.resputil;

import java.util.HashMap;

public class GunMappingJsonResp extends HashMap<String, String> implements GunHttp2ResponseBody {
    @Override
    public String toTransfer() {
        StringBuilder json = new StringBuilder("{\"");
        for (String key : super.keySet()) {
            json.append(key).append("\":\"").append(super.get(key)).append("\",");
        }
        json.append("}");
        return json.toString();
    }
}
