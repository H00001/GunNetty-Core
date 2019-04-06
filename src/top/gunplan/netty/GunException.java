package top.gunplan.netty;

/**
 * std exception in gun netty
 * @author dosdrtt
 */
public class GunException extends RuntimeException {
    private static final long serialVersionUID = 8399807092074881988L;

    public GunException(String why) {
        super(why);
    }

    public GunException(Exception exp) {
        super(exp);
    }

    public Exception getException() {
        return this;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
