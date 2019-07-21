package top.gunplan.netty.impl;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.impl.eventloop.GunNettyTransfer;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * GunNettyCoreThreadManager
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-21 15:18
 */
public interface GunNettyCoreThreadManager {
    /**
     * initInstance
     *
     * @return GunNettyCoreThreadManager
     */
    static GunNettyCoreThreadManager initInstance() {
        return new GunNettyCoreThreadManageImpl();
    }

    /**
     * dealChannelThread
     * is data thread
     *
     * @return GunCoreEventLoop
     */
    GunCoreEventLoop dealChannelThread();


    boolean stopAllAndWait() throws InterruptedException;


    Set<SelectionKey> availableChannel(long i);


    Future<Integer> startAllAndWait();


    /**
     * transferThread
     *
     * @return transferThread
     */
    GunNettyTransfer<SocketChannel> transferThread();


    boolean init(ExecutorService acceptExecutor, ExecutorService dataExecutor, GunNettyPipeline pipeline, int port);


    ManageState runState();
}

