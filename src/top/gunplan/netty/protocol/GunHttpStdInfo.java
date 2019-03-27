package top.gunplan.netty.protocol;

public class GunHttpStdInfo {
    public static class GunCookies {
        private String key;
        private String value;
        private String path;
        private String expries;

        public GunCookies(String key, String value) {
            this(key, value, "/", null);
        }

        public GunCookies(String key, String value, String path, String expries) {
            this.key = key;
            this.value = value;
            this.path = path;
            this.expries = expries;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getExpries() {
            return expries;
        }

        public void setExpries(String expries) {
            this.expries = expries;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value + ";" + "path=" + path + ";expries=" + expries;
        }
    }

    public enum GunHttpRequestType {
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


    public enum statusCode {
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
        TEXT_HTML("text/html;charset=utf-8"), TEXT_JSON("text/json;charset=utf-8"), TEXT_PLAIN("text/plain");
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

