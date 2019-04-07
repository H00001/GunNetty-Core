package top.gunplan.netty.protocol;



/**
 * @author dosdrtt
 */
public interface GunNetOutputInterface extends GunProtoclContorl{
    /**
     * @return bytes[] transfer to client
     */
    byte[] serialize();

}

