package top.gunplan.netty.protocol;

/**
 * @author dosdrtt
 * @version 0.0.0.1
 */
public abstract class AbstractGunHttp2Response implements GunNetOutputInterface, GunProtoclContorl {

    @Override
    public byte[] serialize() {
        return getResponseBody().getBytes();
    }

    /**
     * this function is used to set http response result
     *
     * @return response string
     */
    public abstract String getResponseBody();
}
