package top.gunplan.netty.protocol;

/**
 * @author dosdrtt
 */
public interface GunNetInputInterface {
    /**
     * get the unSerialize result succeed or fail
     * @param in byte[]
     * @return serialize status
     */
    boolean unSerialize(byte[] in);
}
