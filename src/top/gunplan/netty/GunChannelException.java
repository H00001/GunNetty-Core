package top.gunplan.netty;

/**
 * GunChannelException
 *
 * @author frank albert
 * @date 2019-06-30 09:34
 *
 * @version 0.0.0.1
 */

public class GunChannelException extends GunException {
    public GunChannelException(GunExceptionType type, String why) {
        super(type, why);
    }
}
