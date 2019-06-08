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

    /**
     * release resource
     */
    void release();

    /**
     * add resource linked
     */
    void addLink();

}
