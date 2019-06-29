package top.gunplan.netty.protocol;


import top.gunplan.netty.impl.GunNetInputOutputInterface;

/**
 * @author dosdrtt
 */
public interface GunNetOutputInterface extends GunNetInputOutputInterface {
    /**
     * serialize the protoctol
     * @return bytes[] transfer to client
     */
    byte[] serialize();

}

