package top.gunplan.netty.protocol;

import top.gunplan.utils.GunBytesUtil;

/**
 *
 */
public final class GunRPCInputProtocl extends AbstractGunRPCProtocl {
    private boolean analyizeParams(int paramlen, GunBytesUtil.GunReadByteUtil util) {
        parameters = new Object[paramlen];
        for (int i = 0; i < paramlen; i++) {
            parameters[i] = readOnceParam(util);
        }
        return true;
    }

    private boolean writeParam(int paramlen, GunBytesUtil.GunWriteByteUtil util) {
        for (int i = 0; i < paramlen; i++) {
            Object fil = parameters[i];
            writeOnceParam(util, fil);
        }
        return true;
    }

    @Override
    public byte[] serialize() {
        parameters = param.toArray();
        paramleng = (byte) param.size();
        param.clear();
        int len = 9 + methodName.length() + interfaceName.length() + otherCount;
        byte[] serize = new byte[len];
        GunBytesUtil.GunWriteByteUtil serizUtil = new GunBytesUtil.GunWriteByteUtil(serize);
        publicSet(serizUtil);
        serizUtil.writeByte((byte) interfaceName.length());
        serizUtil.write(interfaceName);
        serizUtil.writeByte((byte) methodName.length());
        serizUtil.write(methodName);
        serizUtil.writeByte(paramleng);
        writeParam(paramleng, serizUtil);
        serizUtil.write(endFlage);
        return serize;
    }


    public void poshParam(Integer obj) {
        otherCount += 4 + 1;
        param.push(obj);
    }

    public void poshParam(String obj) {
        otherCount += obj.length() + 2;
        param.push(obj);
    }

    private int otherCount = 0;
    private String methodName;
    private String interfaceName;
    private byte paramleng = 0;


    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public byte getParamleng() {
        return paramleng;
    }

    public void setParamleng(byte paramleng) {
        this.paramleng = paramleng;
    }


    @Override
    public boolean unSerialize(byte[] in) {
        GunBytesUtil.GunReadByteUtil unserizutil = new GunBytesUtil.GunReadByteUtil(in);
        publicUnSet(unserizutil);
        int interlen = unserizutil.readByte();
        this.interfaceName = new String(unserizutil.readByte(interlen));
        int methodlen = unserizutil.readByte();
        this.methodName = new String(unserizutil.readByte(methodlen));
        byte paramlen = unserizutil.readByte();
        this.paramleng = paramlen;
        return paramlen == 0 ? checkEnd(unserizutil) : analyizeParams(paramlen, unserizutil);


    }


}
