package top.gunplan.netty;

import java.util.concurrent.ExecutorService;


/**
 * server interface ,has kinds of implements but now we have only one
 * it is NioStdServerImpl
 *
 * @author dosdrtt
 * @date 2019-04-21
 * @since 0.0.0.1
 */
public interface GunBootServer extends GunBootServerBase {
    /**
     * stop
     * <p>
     * stop the server
     */
    @Override
    int stop() throws InterruptedException;

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
     * boot by synchronized or asynchronized
     *
     * @return sync result
     * @throws GunNettyCanNotBootException exception very urgency
     */
    @Override
    int sync() throws GunNettyCanNotBootException;

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


    enum GunNettyWorkState {


        /**
         *
         */


        STOP(0b0001), SYNC(STOP.state >> 1), ASYNC(STOP.state << 1), RUNNING(STOP.state << 3), BOOT_ERROR_1(-1);


        public int state;

        GunNettyWorkState(int state) {
            this.state = state;
        }
    }

    /**
     * set pipeline
     *
     * @param pipeline pipeline
     * @see GunNettyPipeline
     */

    void setPipeline(GunNettyPipeline pipeline);

}

