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