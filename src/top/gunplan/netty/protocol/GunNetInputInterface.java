package top.gunplan.netty.protocol;

import top.gunplan.netty.impl.GunNetInputOutputInterface;

/**
 * @author dosdrtt
 */
public interface GunNetInputInterface extends GunNetInputOutputInterface {
    /**
     * get the unSerialize result succeed or fail
     * @param in byte[]
     * @return serialize status
     */
    boolean unSerialize(byte[] in);
}
