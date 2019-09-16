/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty;

import java.util.Arrays;

/**
 * GunServerStateManager
 *
 * @author frank albert
 * @version 0.0.0.1
 */
public interface GunServerStateManager {
    /**
     * is or not sync state running
     *
     * @return true or false running
     */
    boolean isSync();


    /**
     * sync type
     * : true sync
     * : false async
     *
     * @param t type
     */
    void setSyncType(boolean t);


    /**
     * get the status of server
     *
     * @return the server 's status
     */
    boolean isRunnable();


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
            if (k == 0) {
                return String.valueOf(STOP);
            }
            StringBuilder builder = new StringBuilder();
            Arrays.stream(values()).forEach((who) -> {
                if ((who.state & k) != 0) {
                    builder.append(who).append("|");
                }
            });
            return builder.toString();
        }

        public static boolean getIsRunning(int k) {
            return (k & GunNettyWorkState.RUNNING.state) != 0;
        }

        public static boolean getIsSync(int k) {
            return (k & GunNettyWorkState.SYNC.state) != 0;
        }
    }

}
