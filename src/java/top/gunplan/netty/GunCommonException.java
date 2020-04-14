package top.gunplan.netty;


/**
 * GunCommonException
 * @author frank
 */
public class GunCommonException extends GunException {
    GunCommonExceptionType type;
    public GunCommonException(GunCommonExceptionType type, Throwable exp) {
        super(GunExceptionMode.CORE, exp);
        this.type = type;
    }

    public GunCommonException(GunCommonExceptionType type, String why) {
        super(GunExceptionMode.CORE, why);
        this.type = type;
    }

    @Override
    public String getMessage() {
        return super.getMessage()+"\n"+type.info;
    }
}
