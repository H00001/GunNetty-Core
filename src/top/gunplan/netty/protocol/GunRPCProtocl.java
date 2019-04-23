package top.gunplan.netty.protocol;

import org.junit.jupiter.api.Test;
import top.gunplan.utils.GunBytesUtil;

import java.io.Serializable;
import java.util.Stack;

public class GunRPCProtocl implements GunNetInputInterface, GunNetOutputInterface {
    @Test
    public void test() {
        GunRPCProtocl it = new GunRPCProtocl();
        it.setInterfaceName("hello");
        it.setMethodName("rpc");
        it.setType(RPCProtoclType.REQUEST);
        it.setCode(RPCProtoclCode.SUCCEED);
        it.poshParam("1234");

        byte[] bom = it.serialize();
        GunRPCProtocl it2 = new GunRPCProtocl();
        it2.unSerialize(bom);
        System.out.println("dd");
    }

    // type 2 method 2 interfaceNamelen 1 interfacename ? methodNamelen 1 methodNamel? paramlen 1 end 2
    private RPCProtoclType type;
    private RPCProtoclCode code;
    private Object[] parameters;
    private int otherCount = 0;
    private Stack<Serializable> param = new Stack<>();

    public void poshParam(Integer obj) {
        otherCount += 4 + 1;
        param.push(obj);
    }

    public void poshParam(String obj) {
        otherCount += obj.length() + 2;
        param.push(obj);
    }

    public RPCProtoclType getType() {
        return type;
    }

    public void setType(RPCProtoclType type) {
        this.type = type;
    }

    public RPCProtoclCode getCode() {
        return code;
    }

    public void setCode(RPCProtoclCode code) {
        this.code = code;
    }

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


    private String methodName;
    private String interfaceName;
    private byte paramleng = 0;
    private byte[] endFlage = {0x0a, 0x05};


    private boolean analyizeParams(int paramlen, GunBytesUtil.GunReadByteUtil util) {
        parameters = new Object[paramlen];
        for (int i = 0; i < paramlen; i++) {
            RPCProtoclParamType ptypei = RPCProtoclParamType.valuefrom(util.readByte());
            switch (ptypei) {
                case INT:

                    parameters[i++] = util.readInt64();
                    break;
                case STRING:
                    byte len = util.readByte();
                    parameters[i++] = new String(util.readByte(len));
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    private boolean writeParam(int paramlen, GunBytesUtil.GunWriteByteUtil util) {
        for (int i = 0; i < paramlen; i++) {
            Object fil = parameters[i];
            if (fil instanceof Integer) {
                util.writeByte(RPCProtoclParamType.INT.val);
                util.write64((Integer) fil);

            } else if (fil instanceof String) {
                util.writeByte(RPCProtoclParamType.STRING.val);
                util.writeByte((byte) ((String) parameters[i]).length());
                util.write((String) parameters[i]);
            }

        }
        return true;
    }


    @Override
    public boolean unSerialize(byte[] in) {
        GunBytesUtil.GunReadByteUtil unserizutil = new GunBytesUtil.GunReadByteUtil(in);
        this.type = RPCProtoclType.valuefrom(unserizutil.readInt());
        this.code = RPCProtoclCode.valuefrom(unserizutil.readInt());
        int interlen = unserizutil.readByte();
        this.interfaceName = new String(unserizutil.readByte(interlen));
        int methodlen = unserizutil.readByte();
        this.methodName = new String(unserizutil.readByte(methodlen));
        byte paramlen = unserizutil.readByte();
        this.paramleng = paramlen;
        return paramlen == 0 ? checkEnd(unserizutil) : analyizeParams(paramlen, unserizutil);


    }

    private boolean checkEnd(GunBytesUtil.GunReadByteUtil unserizutil) {
        byte[] end = unserizutil.readByte(2);
        return GunBytesUtil.compareBytesFromEnd(end, endFlage[0], endFlage[1]);
    }

    @Override
    public byte[] serialize() {
        parameters = param.toArray();
        paramleng = (byte) param.size();
        param.clear();
        int len = 9 + methodName.length() + interfaceName.length() + otherCount;
        byte[] serize = new byte[len];
        GunBytesUtil.GunWriteByteUtil serizUtil = new GunBytesUtil.GunWriteByteUtil(serize);
        serizUtil.write(type.value);
        serizUtil.write(code.value);
        serizUtil.writeByte((byte) interfaceName.length());
        serizUtil.write(interfaceName);
        serizUtil.writeByte((byte) methodName.length());
        serizUtil.write(methodName);
        serizUtil.writeByte(paramleng);
        writeParam(paramleng, serizUtil);
        serizUtil.write(endFlage);
        return serize;
    }

}
