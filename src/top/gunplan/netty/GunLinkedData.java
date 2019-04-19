package top.gunplan.netty;

/**
 * @author dosdrtt
 */
public interface GunLinkedData {
    /**
     * get data method
     * @return data
     */
    byte[] getData();

    void release();

    void addLink();

}
