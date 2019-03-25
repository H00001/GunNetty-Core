package top.gunplan.netty.protocol;

/**
 * @author dosdrtt
 */
public abstract class AbstractGunHttp2Response implements GunNetResponseInterface {
    @Override
    public byte[] serialize() {
        return getResponseBody().getBytes();
    }

    /**
     * this function is used to set http response result
     * @return response string
     */
    public abstract String getResponseBody();
}
