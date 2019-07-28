package top.gunplan.netty.protocol;


import top.gunplan.netty.impl.GunNetBound;

/**
 * GunNetOutbound
 *
 * @author dosdrtt
 */
public interface GunNetOutbound extends GunNetBound {
    /**
     * serialize the protocol
     *
     * @return bytes[] transfer to client
     */
    byte[] serialize();

}

