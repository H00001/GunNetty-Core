package top.gunplan.netty.impl.channel;

import top.gunplan.netty.ExceptionType;

/**
 * GunNettyChannelExceptionType
 * @author frank
 */
public enum GunNettyChannelExceptionType implements ExceptionType {
    /**
     *
     */
    TRANSLATE_ERROR("Translate error");

    String info;

    GunNettyChannelExceptionType(String info){
        this.info = info;
    }

    @Override
    public String info() {
        return info;
    }
}
