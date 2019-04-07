package top.gunplan.netty.protocol;



/**
 * @author dosdrtt
 */
public interface GunNetOutputInterface {
    /**
     * @return bytes[] transfer to client
     */
    byte[] serialize();

    /**
     * get is or not return
     * @return is or not return to client
     */
    boolean isReturn();
}

