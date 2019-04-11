package top.gunplan.netty.protocol.resputil;

import java.util.HashMap;


/**
 * @author dosdrtt
 */
public class GunMappingJsonResp extends HashMap<String, String> implements GunHttp2ResponseBody {

    private static final long serialVersionUID = -6884797417624222041L;

    @Override
    public String toTransfer() {
        StringBuilder json = new StringBuilder("{\"");
        for (String key : super.keySet()) {
            json.append(key).append("\":\"").append(super.get(key)).append("\",");
        }
        String s = json.substring(0, json.length() - 1);
        s += "}";
        return s;
    }
}
