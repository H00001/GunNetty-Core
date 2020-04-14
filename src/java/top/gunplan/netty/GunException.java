/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

/**
 * std exception in gun netty
 *
 * @author dosdrtt
 */
public class GunException extends RuntimeException {
    private static final long serialVersionUID = 8399807092074881988L;

    private final GunExceptionMode type;

    public GunException(GunExceptionMode type, String why) {
        super(why);
        this.type = type;
    }


    public GunException(GunExceptionMode type, Throwable exp) {
        super(exp);
        this.type = type;
    }

    public Exception getException() {
        return this;
    }

    @Override
    public String getMessage() {
        return this.type.info();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
