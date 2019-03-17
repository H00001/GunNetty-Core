package top.gunplan.netty.protocol;

/**
 * @author dosdrtt
 */
public abstract class CunBaseHttp2Response implements GunHttp2Response {
    private GunHttpStdInfo.HttpProtoclType protoclType;
    private GunHttpStdInfo.statusCode code;
    private GunHttpStdInfo.ContentType contentType;

    @Override
    public String toResponse() {
        return null;
    }

    @Override
    public byte[] serizResponse() {
        StringBuilder http2resp = new StringBuilder();
        http2resp.append(protoclType.getVal());
        http2resp.append(" ");
        http2resp.append(code);
        http2resp.append(" ");
        http2resp.append(code.getVal());
        return http2resp.toString().getBytes();

    }


}
