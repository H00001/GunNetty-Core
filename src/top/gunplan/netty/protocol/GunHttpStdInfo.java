package top.gunplan.netty.protocol;

public class GunHttpStdInfo {
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

    public enum HttpProtoclType {
        /**
         *
         */
        HTTP1_1("HTTP/1.1"), HTTP2_0("HTTP/2.0");
        private String val;

        HttpProtoclType(String val) {
            this.val = val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }


    enum statusCode {
        /**
         *
         */
        OK(200), FORBIDDEN(403), NOTFOUND(404);
        private int val;

        statusCode(int val) {
            this.val = val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

    }

    public enum ContentType {
        /**
         *
         */
        TEXT_HTML("text/html"), TEXT_JSON("text/json"), TEXT_PLAIN("text/plain");
        private String val;

        ContentType(String val) {
            this.val = val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

}

