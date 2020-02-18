package top.gunplan.netty;

/**
 * GunCommonException
 * @author frank
 */
enum GunCommonExceptionType implements ExceptionType{
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
