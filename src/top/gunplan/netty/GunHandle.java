package top.gunplan.netty;

/**
 * this is a handle interface ,include {@link GunNettyFilter }
 * and {@link GunNettyHandle}
 *
 * @author dosdrtt
 * @since 0.0.0.3
 */
public interface GunHandle {
    /**
     * <p></p>
     * nothing it it
     * <p>
     *
     * @return int init result 0:succeed
     * 1:error
     */
    default int init() {
        return 0;
    }


    /**
     * this function will be called at destroy
     *
     * @return int destroy result
     */

    default int destroy() {
        return 0;
    }


}
