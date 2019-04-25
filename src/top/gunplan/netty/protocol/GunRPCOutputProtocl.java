package top.gunplan.netty.protocol;

import top.gunplan.utils.GunBytesUtil;

/**
 *
 */
public class GunRPCOutputProtocl extends AbstractGunRPCProtocl {
    @Override
    public boolean unSerialize(byte[] in) {
        GunBytesUtil.GunReadByteUtil util = new GunBytesUtil.GunReadByteUtil(in);
        publicUnSet(util);
        returnValue = readOnceParam(util);
        return checkEnd(util);
    }

    private Object returnValue;

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }


    @Override
    public byte[] serialize() {
        int len = returnValue instanceof Integer ? 6 + 5 : 6 + ((String) returnValue).length() + 2;
        byte[] serize = new byte[len];
        GunBytesUtil.GunWriteByteUtil serizUtil = new GunBytesUtil.GunWriteByteUtil(serize);
        publicSet(serizUtil);
        writeOnceParam(serizUtil, returnValue);
        serizUtil.write(endFlage);
        return serize;
    }
}
