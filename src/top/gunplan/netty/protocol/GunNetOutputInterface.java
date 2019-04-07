package top.gunplan.netty.protocol;



/**
 * @author dosdrtt
 */
public interface GunNetOutputInterface {
    /**
     * @return bytes[] transfer to client
     */
    byte[] serialize();

}

