package top.gunplan.netty.protocol;

import top.gunplan.netty.protocol.GunHttp2ResponseInterface;

/**
 * @author dosdrtt
 */
public interface GunNetResponseInterface {
    /**
     * @return bytes[]
     */
    byte[] serialize();

    /**
     * @return
     */
    boolean isReturn();
}

