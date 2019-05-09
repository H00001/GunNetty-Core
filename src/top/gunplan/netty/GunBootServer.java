package top.gunplan.netty;

import java.util.concurrent.ExecutorService;


/**
 * server interface ,has kinds of implements but now we have only one
 * it is NioStdServerImpl
 *
 * @author dosdrtt
 * @since 0.0.0.1
 */
public interface GunBootServer extends GunBootServerBase {
    GunBootServer registerObserve(GunNettyObserve observe);

    /**
     * get the status of server
     *
     * @return the server 's status
     */
    boolean isRunnable();


    /**
     * set the Thread pool that dispose the request
     *
     * @param acceptExecuters  this Executer is used to deal with accept request
     * @param requestExecuters this Executer is used to deal with data request
     * @return this
     */

    GunBootServer setExecuters(ExecutorService acceptExecuters, ExecutorService requestExecuters);

    /**
     * this method return a {@link GunPileline} implements
     *
     * @return GunPileline
     */
    GunPileline getPipeline();

//    /**
//     * the function is used to add filter
//     *
//     * @param filter filter, filter the request
//     * @return GunBootServer chain style
//     */
//
//
//    /**
//     * @param clazz class to deal
//     * @throws ClassNotFoundException class not found
//     * @apiNote
//     */
//    void inintObject(@NotNull Class<? extends GunHandle> clazz) throws Exception;

    /**
     * check it can or not be boot
     *
     * @return check result true is can be boot
     */
    boolean initCheck();


    void stop();

}

