package top.gunplan.netty.protocol;

/**
 * @author dosdrtt
 */

public enum RPCProtoclType {
    /**
     *
     */
    REQUEST(0x0001), RESPONSE(0x0002);
    int value;

    RPCProtoclType(int i) {
        this.value = i;
    }

    public static RPCProtoclType valuefrom(int val) {
        RPCProtoclType[] types = values();
        for (RPCProtoclType tp : types) {
            if (tp.value == val) {
                return tp;
            }
        }
        return null;
    }
}
