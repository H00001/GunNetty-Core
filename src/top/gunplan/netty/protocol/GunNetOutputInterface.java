package top.gunplan.netty.protocol;



/**
 * @author dosdrtt
 */
public interface GunNetOutputInterface {
    /**
     * serialize the protoctol
     * @return bytes[] transfer to client
     */
    byte[] serialize();

}

