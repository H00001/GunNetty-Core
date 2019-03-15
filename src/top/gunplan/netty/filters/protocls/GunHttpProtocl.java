package top.gunplan.netty.filters.protocls;

import top.gunplan.netty.anno.GunNetFilterOrder;
import top.gunplan.netty.filters.GunRequestProtocl;


import java.util.HashMap;

/**
 * @author dosdrtt
 */
@GunNetFilterOrder(index = 0)
public class GunHttpProtocl implements GunRequestProtocl {
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

    public GunHttpProtocl() {

    }

    public GunHttpProtocl(String requestHeadFirst, HashMap<String, String> requstHead, GunHttpRequestType method) {
        this.requestHeadFirst = requestHeadFirst;
        this.requstHead = requstHead;
        this.method = method;
    }

    private String requestHeadFirst;
    private String requestBody;
    private HashMap<String, String> requstHead = new HashMap<>();

    public String getRequestBody() {
        return requestBody;
    }

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

    public String getRequestHeadFirst() {

        return requestHeadFirst;
    }

    public void setRequestHeadFirst(String requestHeadFirst) {
        this.requestHeadFirst = requestHeadFirst;
    }

    @Override
    public byte[] seriz() {
        return new byte[0];
    }

    @Override
    public boolean unSeriz(byte[] in) {
        String httpconetnt = new String(in);

        int postion = httpconetnt.indexOf("\r\n");
        this.requestHeadFirst = httpconetnt.substring(0, postion);
        httpconetnt = httpconetnt.substring(postion + 2, httpconetnt.length());
        int spiltpoint = httpconetnt.indexOf("\r\n\r\n");
        String[] httphead = httpconetnt.substring(0, spiltpoint).split("\r\n");
        analyzingHttpHead(httphead);

        if (requestHeadFirst.startsWith(GunHttpRequestType.GET.getVal())) {
            this.method = GunHttpRequestType.GET;
//            functionToDealGetMethod(httpconetnt);
        } else if (requestHeadFirst.startsWith("POST")) {
            this.method = GunHttpRequestType.POST;
            functionToDealPostMethod();
        }


        return true;
    }


    private void functionToDealPostMethod() {

    }


    private void analyzingHttpHead(String[] httphead) {
        for (String eachhead : httphead) {
            requstHead.put(eachhead.split(":")[0], eachhead.split(":")[1]);
        }
    }
}
