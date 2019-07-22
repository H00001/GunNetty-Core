package top.gunplan.netty.impl;

/**
 * ManageState
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-07-21 16:17
 */
public enum ManageState {
    /**
     * INACTIVE   : stopd , not active
     * BOOTING    : booting ,boot but not complete
     * RUNNING    : service is running
     * STOPPING   : stop is running but not stopd
     */
    INACTIVE(1), BOOTING(1 << 1), RUNNING(1 << 2), STOPPING(1 << 3);
    int stateCode;

    ManageState(int stateCode) {
        this.stateCode = stateCode;
    }
}
