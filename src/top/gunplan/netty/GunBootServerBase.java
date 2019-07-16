package top.gunplan.netty;


/**
 * @author dosdrtt
 * @date 2019/05/25
 */

public interface GunBootServerBase {
    /**
     * sync
     * start sync server and wait
     *
     * @return int boot result
     * @throws Exception syncing's exception
     */

    int sync() throws Exception;


    /**
     * stop server
     *
     * @return stop result
     * @throws InterruptedException when it was interrupted
     */
    int stop() throws InterruptedException;

    /**
     * is or not synchronized
     *
     * @return result
     */
    default boolean isSync() {
        return true;
    }


    class GunNettyCanNotBootException extends GunException {

        private static final long serialVersionUID = -8015693220955781128L;

        public GunNettyCanNotBootException(Exception why) {
            super(GunExceptionType.URGENCY, why);
        }
    }
}
