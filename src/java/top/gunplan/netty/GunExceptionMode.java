/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

/**
 * GunExceptionType
 * Exception types
 *
 * @author dosdrtt
 */
public enum GunExceptionMode implements ExceptionType{
    /**
     * types such as
     */
    CHANNEL("module:channel"),EVENT_LOOP("module:event_loop"),
    CORE("Core:"),
    NULLPTR("Nullptr exception"), URGENCY("Urgency can incur boot fail"),
    REF("Reference exception"), READ_PROPERTY_ERROR("Read property error"),
    EXC0("Init error"),
    OUT_POSITION("Out of position"), STATE_ERROR("State error"),
    NOT_SUPPORT("Not support");
    private String info;

    GunExceptionMode(String info) {
        this.info = info;
    }

    @Override
    public String info() {
        return info;
    }
}

