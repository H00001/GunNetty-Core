package top.gunplan.netty.protocol;


import top.gunplan.netty.impl.GunNetBound;

/**
 * GunNetOutBound
 *
 * @author dosdrtt
 */
public interface GunNetOutBound extends GunNetBound {
    /**
     * serialize the protocol
     *
     * @return bytes[] transfer to client
     */
    byte[] serialize();

}

