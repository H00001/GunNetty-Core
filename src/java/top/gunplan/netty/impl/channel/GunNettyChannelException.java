package top.gunplan.netty.impl.channel;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionMode;

/**
 * @author frank
 */
public class GunNettyChannelException extends GunException {
    GunNettyChannelExceptionType type;

    public GunNettyChannelException(GunNettyChannelExceptionType type, Throwable exp) {
        super(GunExceptionMode.CHANNEL, exp);
        this.type = type;
    }

    public GunNettyChannelException(GunNettyChannelExceptionType type, String why) {
        super(GunExceptionMode.CHANNEL, why);
        this.type = type;
    }

    @Override
    public String getMessage() {
        return super.getMessage()+"\n"+type.info;
    }
}
