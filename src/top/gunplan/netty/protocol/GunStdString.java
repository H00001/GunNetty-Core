package top.gunplan.netty.protocol;

public class GunStdString implements GunNetRequestInterface {
    private String s=null;
    @Override
    public boolean unSerialize(byte[] in) {
        s = new String(in);
        return true;
    }

    public String getS() {
        return s;
    }
}
