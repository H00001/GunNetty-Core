package top.gunplan.netty;

/**
 * std exception in gun netty
 *
 * @author dosdrtt
 */
public class GunException extends RuntimeException {
    private static final long serialVersionUID = 8399807092074881988L;

    private final GunExceptionTypes type;

    public GunException(String why) {
        this(GunExceptionTypes.EXC3, why);
    }

    public GunException(GunExceptionTypes type, String why) {
        super(why);
        this.type = type;
    }

    public GunException(Exception exp) {
        this(GunExceptionTypes.EXC3, exp);
    }

    public GunException(GunExceptionTypes type, Exception exp) {
        super(exp);
        this.type = GunExceptionTypes.EXC3;
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
