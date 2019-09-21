/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl;

/**
 * ManagerState
 *
 * @author frank albert
 * @version 0.0.0.2
 */
public enum ManagerState {
    /**
     * INACTIVE   : stop , not active
     * BOOTING    : booting ,boot but not complete
     * RUNNING    : service is running
     * STOPPING   : stop is running but not stopd
     */
    INACTIVE(1), BOOTING(1 << 1), RUNNING(1 << 2), STOPPING(1 << 3);
    int stateCode;

    ManagerState(int stateCode) {
        this.stateCode = stateCode;
    }
}
