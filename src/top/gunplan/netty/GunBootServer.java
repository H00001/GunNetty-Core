/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import top.gunplan.netty.impl.GunNettyCoreThreadManager;
import top.gunplan.netty.observe.GunNettyServicesObserve;

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
     *
     * @return stop result
     * 0:normal
     * other value : not normal
     * @throws InterruptedException when stoping the server
     */
    @Override
    int stop() throws InterruptedException;

    /**
     * register observe
     *
     * @param observe observe object
     * @return GunBootServer
     */
    GunBootServer registerObserve(GunNettyServicesObserve observe);

    /**
     * GunNettyCoreThreadManager
     *
     * @return manager
     */
    GunNettyCoreThreadManager manager();


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
     * @param acceptExecutor  this Executor is used to deal with accept request
     * @param requestExecutor this Executor is used to deal with data request
     * @return this
     */

    GunBootServer setExecutors(ExecutorService acceptExecutor, ExecutorService requestExecutor);


    /**
     * check it can or not be boot
     *
     * @return check result true is can be boot
     */
    boolean initCheck();


    enum GunNettyWorkState {


        /**
         * STOP          :not boot, init state or stopped state
         * SYNC          :sync running
         * ASYNC         :async running
         * RUNNING       :running include:sync running,async running
         * BOOT_ERROR_1  :when boot error's stop state
         */

        STOP(0b0000), SYNC(0b0001), ASYNC(0b0010), RUNNING(0b0100), BOOT_ERROR_1(0b1000), BOOT_ERROR_2(0b1001);


        public int state;

        GunNettyWorkState(int state) {
            this.state = state;
        }

        public static String getState(int k) {
            StringBuilder builder = new StringBuilder();
            for (GunNettyWorkState i : values()) {
                if ((i.state & k) != 0) {
                    builder.append(i).append("|");
                }
            }
            return builder.toString();
        }
    }


    /**
     * when has a new channel
     *
     * @param initHandle input the pipeline
     */

    void onHasChannel(ChannelInitHandle initHandle);

    /**
     * server event handle
     *
     * @param handle handle
     */
    void whenServerChannelStateChanged(SystemChannelChangedHandle handle);


    /**
     * registerGlobalTimers
     *
     * @param timer time event
     * @return this
     */
    GunBootServer registerGlobalTimers(GunNettyTimer timer);
}

