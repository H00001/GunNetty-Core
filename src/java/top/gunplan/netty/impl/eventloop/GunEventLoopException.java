/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionMode;

enum GunEventLoopExceptionType {
    /**
     *
     */
    EXECUTE_TASK_FAIL("Execute task fail"),
    LOOP_ERROR("Loop error");

    private String info;

    GunEventLoopExceptionType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}

/**
 * GunEventLoopException
 * @author frank
 */
public class GunEventLoopException extends GunException {

    private GunEventLoopExceptionType type;

    public GunEventLoopException(GunEventLoopExceptionType type, Throwable why) {
        super(GunExceptionMode.EVENT_LOOP,why);
        this.type = type;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + type.getInfo();
    }
}
