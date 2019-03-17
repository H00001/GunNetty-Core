package top.gunplan.netty.protocol;

/**
 * @author dosdrtt
 */
public abstract class CunBaseHttp2Response implements GunHttp2ResponseInterface {
    private GunHttpStdInfo.HttpProtoclType protoclType = GunHttpStdInfo.HttpProtoclType.HTTP1_1;
    private GunHttpStdInfo.statusCode code = GunHttpStdInfo.statusCode.OK;
    private GunHttpStdInfo.ContentType contentType = GunHttpStdInfo.ContentType.TEXT_JSON;

    @Override
    public String toResponse() {
        return null;
    }

    @Override
    public byte[] serizResponse() {
        StringBuilder http2resp = new StringBuilder();
        http2resp.append(protoclType.getVal());
        http2resp.append(" ");
        http2resp.append(code.getVal());
        http2resp.append(" ");
        http2resp.append(code);
        return http2resp.toString().getBytes();

    }


}
