package top.gunplan.netty.protocol;

public enum RPCProtoclParamType {
    /**
     *
     */
    INT((byte) 0x01), STRING((byte) 0x02),ERROR((byte)0x1c);
    byte val;

    RPCProtoclParamType(byte val) {
        this.val = val;
    }

    public static RPCProtoclParamType valuefrom(byte val) {
        RPCProtoclParamType[] types = values();
        for (RPCProtoclParamType tp : types) {
            if (tp.val == val) {
                return tp;
            }
        }
        return ERROR;
    }
}
