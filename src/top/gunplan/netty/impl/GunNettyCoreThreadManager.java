package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyBaseObserve;
import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.impl.eventloop.GunDataEventLoop;
import top.gunplan.netty.impl.eventloop.GunNettyTransfer;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * GunNettyCoreThreadManager
 *
 * @author frank albert
 * @version 0.0.h.1
 * @date 2019-07-21 15:18
 */
public interface GunNettyCoreThreadManager {
    /**
     * initInstance
     *
     * @param observe event observe
     * @param property core property
     * @return GunNettyCoreThreadManager
     */
    static GunNettyCoreThreadManager initInstance(final GunNettyCoreProperty property, final GunNettyBaseObserve observe) {
        return new GunNettyCoreThreadManageImpl(property, observe);
    }

    /**
     * dealChannelEventLoop
     * is data thread
     * who can deal channel event? only Data EventLoop
     *
     * @return GunDataEventLoop
     */
    GunDataEventLoop<SocketChannel> dealChannelEventLoop();


    /**
     * stop all and wait for stop
     *
     * @return result
     * @throws InterruptedException when something happened
     */
    boolean stopAndWait() throws InterruptedException;


    /**
     * i th available channel
     *
     * @param i i th
     * @return channel's collection
     */
    Set<SelectionKey> availableChannel(long i);


    /**
     * boot server
     *
     * @return future result
     */
    Future<Integer> startAndWait();


    /**
     * transferThread
     *
     * @return transferThread
     */
    GunNettyTransfer<SocketChannel> transferThread();


    /**
     * init
     *
     * @param acceptExecutor executor to deal accept event
     * @param dataExecutor   executor to deal data event
     * @param pipeline       pipeline deal handle
     * @param port           listen the port
     * @return init result
     * @throws IOException when i/o error
     */
    boolean init(final ExecutorService acceptExecutor, final ExecutorService dataExecutor, GunNettyPipeline pipeline, int port) throws IOException;


    /**
     * running state
     *
     * @return manage state
     */
    ManagerState runState();
}

