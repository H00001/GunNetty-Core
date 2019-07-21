package top.gunplan.netty.impl.eventloop;

/**
 * GunNettyVariableWorker
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-21 15:12
 */
public interface GunNettyVariableWorker {

    void startEventLoop();

    boolean isRunning();

    void stopEventLoop();
}
