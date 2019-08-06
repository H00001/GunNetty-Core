/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
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
        this.type = GunExceptionType.EXC3;
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
