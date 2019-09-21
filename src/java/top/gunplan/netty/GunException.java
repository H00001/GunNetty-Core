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

    private final GunExceptionType type;


    public GunException(GunExceptionType type, String why) {
        super(why);
        this.type = type;
    }

    public GunException(Throwable exp) {
        this(GunExceptionType.EXC3, exp);
    }

    public GunException(GunExceptionType type, Throwable exp) {
        super(exp);
        this.type = type;
    }

    public Exception getException() {
        return this;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + this.type.getInfo();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
