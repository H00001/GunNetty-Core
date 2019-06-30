package top.gunplan.netty;

/**
 * GunExceptionType
 * Exception types
 *
 * @author dosdrtt
 * @date 2019/05/31
 */
public enum GunExceptionType {
    /**
     * types such as
     */
    NULLPTR("nullptr exception"),URGERCY("urgency can incur boot fail"), REF("reference exception"), READ_PROPERTY_ERROR("READ PROPERTY ERROR"), EXC0("Init error"), EXC1(""), EXC2(""), OUT_POSITION("out of position"), EXC3("None know exc3");
    private String info;

    GunExceptionType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
