/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

/**
 * GunCommonException
 * @author frank
 */
public enum GunCommonExceptionType implements ExceptionType {
    /**
     *
     */
    FUNCTION_NOT_IMPLEMENT("Function not implement"),
    FUNCTION_NOT_SUPPORT("Function not support");
    String info;
    GunCommonExceptionType(String info){
        this.info = info;
    }
    @Override
    public String info() {
        return null;
    }
}