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
public enum GunExceptionMode {
    /**
     * types such as
     */
    CHANNEL("module:channel"),EVENT_LOOP("module:event_loop"),
    NULLPTR("Nullptr exception"), URGENCY("Urgency can incur boot fail"),
    REF("Reference exception"), READ_PROPERTY_ERROR("Read property error"),
    EXC0("Init error"), EXC1("Connection error"), EXC2("Use high level"),
    OUT_POSITION("Out of position"), STATE_ERROR("State error"),
    CHANNEL_ERROR("channel error"),
    EXC3("None know exc3"), FUNCTION_NOT_IMPLEMENT("Function not implement"),
    TRANSLATE_ERROR("Translate error"), NOT_SUPPORT("Not support");
    private String info;

    GunExceptionMode(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
