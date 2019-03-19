package top.gunplan.netty.protocol;

/**
 * @author dosdrtt
 */
public abstract class BaseGunHttp2Response implements GunHttp2ResponseInterface {
    private GunHttpStdInfo.HttpProtoclType protoclType = GunHttpStdInfo.HttpProtoclType.HTTP1_1;
    private GunHttpStdInfo.statusCode code = GunHttpStdInfo.statusCode.OK;
    private GunHttpStdInfo.ContentType contentType = GunHttpStdInfo.ContentType.TEXT_JSON;

    private boolean iswrite;


    public GunHttpStdInfo.HttpProtoclType getProtoclType() {
        return protoclType;
    }

    public void setProtoclType(GunHttpStdInfo.HttpProtoclType protoclType) {
        this.protoclType = protoclType;
    }

    public GunHttpStdInfo.statusCode getCode() {
        return code;
    }

    public void setCode(GunHttpStdInfo.statusCode code) {
        this.code = code;
    }

    public GunHttpStdInfo.ContentType getContentType() {
        return contentType;
    }

    public void setContentType(GunHttpStdInfo.ContentType contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean isReturn() {
        return iswrite;
    }

    public boolean isIswrite() {
        return iswrite;
    }

    public void setIswrite(boolean iswrite) {
        this.iswrite = iswrite;
    }

    @Override
    public byte[] serialize() {
        StringBuilder http2resp = new StringBuilder();
        http2resp.append(protoclType.getVal());
        http2resp.append(" ");
        http2resp.append(code.getVal());
        http2resp.append(" ");
        http2resp.append(code).append("\r\n");
        http2resp.append("Content-Type:").append(contentType.getVal()).append("\r\n");
        http2resp.append("Content-Length:").append(this.toResponse().length()).append("\r\n\r\n");
        http2resp.append(this.toResponse());
        return http2resp.toString().getBytes();

    }


}
