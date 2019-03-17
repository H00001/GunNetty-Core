package top.gunplan.netty.protocol;

public interface GunHttp2Response {
    String toResponse();

    byte[] serizResponse();
}
