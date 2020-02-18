/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;


/**
 * @author dosdrtt
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


    class GunNettyCanNotBootException extends GunException {

        private static final long serialVersionUID = -8015693220955781128L;

        public GunNettyCanNotBootException(Throwable why) {
            super(GunExceptionMode.URGENCY, why);
        }

        public GunNettyCanNotBootException(String why) {
            super(GunExceptionMode.URGENCY, why);
        }
    }
}
