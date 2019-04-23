package top.gunplan.netty.protocol;

/**
 * @author dosdrtt
 */

public enum RPCProtoclCode {
    /**
     *
     */
    SUCCEED(0x00);
    int value;

    RPCProtoclCode(int i) {
        this.value = i;
    }

    public static RPCProtoclCode valuefrom(int val) {
        RPCProtoclCode[] types = values();
        for (RPCProtoclCode tp : types) {
            if (tp.value == val) {
                return tp;
            }
        }
        return null;
    }
}
