package top.gunplan.netty;

/**
 * GunExceptionTypes
 * Exception types
 *
 * @author dosdrtt
 * @date 2019/05/31
 */
public enum GunExceptionTypes {
    /**
     * types such as
     */
    NULLPTR("nullptr exception"), REF("reference exception"), EXC0(""), EXC1(""), EXC2(""), EXC3("None know exc3");
    private String info;

    GunExceptionTypes(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
