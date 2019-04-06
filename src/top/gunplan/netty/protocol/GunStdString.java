package top.gunplan.netty.protocol;

public class GunStdString implements GunNetInputInterface {
    private String value =null;
    @Override
    public boolean unSerialize(byte[] in) {
        value = new String(in);
        return true;
    }

    public String getString() {
        return value;
    }
}
