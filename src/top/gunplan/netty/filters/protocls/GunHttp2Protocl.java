package top.gunplan.netty.filters.protocls;

import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.filters.GunRequestProtocl;

import java.util.HashMap;


/**
 * @author dosdrtt
 */
@GunNetFilterOrder(index = 0)
public class GunHttp2Protocl implements GunRequestProtocl {
    enum GunHttpRequestType {
        /**
         *
         *
         */
        GET("GET"), POST("POST"), DELETE("DELETE"), PUT("PUT");
        private String val;

        GunHttpRequestType(String val) {
            this.val = val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

    public GunHttp2Protocl() {

    }

    public GunHttp2Protocl(String requestHeadFirst, HashMap<String, String> requstHead, GunHttpRequestType method) {
        this.requstHead = requstHead;
        this.method = method;
    }

    private String requestBody;
    private HashMap<String, String> requstHead = new HashMap<>();
    private HashMap<String, String> http2Parameter = new HashMap<>(2);

    public String getRequestBody() {
        return requestBody;
    }

    private String requestUrl;

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    private GunHttpRequestType method;

    public HashMap<String, String> getRequstHead() {
        return requstHead;
    }

    public void setRequstHead(HashMap<String, String> requstHead) {
        this.requstHead = requstHead;
    }

    public GunHttpRequestType getMethod() {
        return method;
    }


    public void setMethod(GunHttpRequestType method) {
        this.method = method;
    }

    @Override
    public byte[] seriz() {
        return new byte[0];
    }

    @Override
    public boolean unSeriz(byte[] in) {
        String httpconetnt = new String(in);

        int postion = httpconetnt.indexOf("\r\n");
        this.analyzingHttpHeadFirst(httpconetnt.substring(0, postion));
        int spiltpoint = httpconetnt.indexOf("\r\n\r\n");
        this.analyzingHttpHead(httpconetnt.substring(postion + 2, spiltpoint).split("\r\n"));


        if (method == GunHttpRequestType.GET) {
            this.method = GunHttpRequestType.GET;
//            functionToDealGetMethod(httpconetnt);
        } else if (method == GunHttpRequestType.POST) {
            this.method = GunHttpRequestType.POST;
            functionToDealPostMethod();
        }


        return true;
    }


    private void functionToDealPostMethod() {


    }

    private void analyzingHttpHeadFirst(final String httpHeadFirst) {
        do {
            if (httpHeadFirst.startsWith(GunHttpRequestType.GET.getVal())) {
                this.method = GunHttpRequestType.GET;
                break;
            } else if (httpHeadFirst.startsWith(GunHttpRequestType.POST.getVal())) {
                this.method = GunHttpRequestType.POST;
                break;
            }
            if (httpHeadFirst.startsWith(GunHttpRequestType.PUT.getVal())) {
                this.method = GunHttpRequestType.PUT;
                break;
            }
            if (httpHeadFirst.startsWith(GunHttpRequestType.DELETE.getVal())) {
                this.method = GunHttpRequestType.DELETE;
                break;
            }
        } while (false);
        String requrl = httpHeadFirst.split(" ")[1];
        var3310:
        if (requrl.contains("?")) {
            this.requestUrl = requrl.split("\\?")[0];
            String parameters[];
            if (requrl.split("\\?")[1].contains("&")) {
                parameters = requrl.split("&");
            } else {
                parameters = new String[1];
                parameters[0] = requrl.split("\\?")[1];
            }
            for (String parameter : parameters) {
                http2Parameter.put(parameter.split("=")[0], parameter.split("=")[1]);
            }

        } else {
            this.requestUrl = requrl;
        }
    }


    private void analyzingHttpHead(String[] httphead) {
        for (String eachhead : httphead) {
            requstHead.put(eachhead.split(":")[0], eachhead.split(":")[1]);
        }
    }
}
