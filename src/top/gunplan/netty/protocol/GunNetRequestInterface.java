package top.gunplan.netty.protocol;

/**
 * @author dosdrtt
 */
public interface GunNetRequestInterface {
    /**
     * @param in byte[]
     * @return serialize status
     */
    boolean unSerialize(byte[] in);
}
