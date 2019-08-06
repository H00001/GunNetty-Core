/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

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


    /**
     * sync type
     * : true sync
     * : false async
     *
     * @param b type
     */
    default void setSyncType(boolean b) {
        throw new GunException(GunExceptionType.FUNCTION_NOT_IMPLEMENT, ":setSyncType");
    }

    class GunNettyCanNotBootException extends GunException {

        private static final long serialVersionUID = -8015693220955781128L;

        public GunNettyCanNotBootException(Throwable why) {
            super(GunExceptionType.URGENCY, why);
        }
    }
}
