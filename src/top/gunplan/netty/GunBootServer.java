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
    /**
     * register observe
     *
     * @param observe observe object
     * @return GunBootServer
     */
    GunBootServer registerObserve(GunNettyObserve observe);

    /**
     * get the status of server
     *
     * @return the server 's status
     */
    boolean isRunnable();


    /**
     * sync
     * boot by synchorized
     *
     * @return sync result
     * @throws Exception exp
     */
    @Override
    int sync() throws Exception;

    /**
     * set the Thread pool that dispose the request
     *
     * @param acceptExecuters  this Executer is used to deal with accept request
     * @param requestExecuters this Executer is used to deal with data request
     * @return this
     */

    GunBootServer setExecuters(ExecutorService acceptExecuters, ExecutorService requestExecuters);

    /**
     * this method return a {@link GunNettyPipeline} implements
     *
     * @return GunNettyPipeline
     */
    GunNettyPipeline getPipeline();


    /**
     * check it can or not be boot
     *
     * @return check result true is can be boot
     */
    boolean initCheck();


    /**
     * stop
     *
     * stop the server
     */
    void stop();

    /**
     * set pipeline
     *
     * @param pipeline pipeline
     * @see GunNettyPipeline
     */

    void setPipeline(GunNettyPipeline pipeline);

}

