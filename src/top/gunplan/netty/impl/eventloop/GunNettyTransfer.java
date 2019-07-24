package top.gunplan.netty.impl.eventloop;


import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettyPipeline;

import java.nio.channels.SelectableChannel;
import java.util.concurrent.ExecutorService;

/**
 * GunNettyTransfer
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-06-19 00:28
 */

public interface GunNettyTransfer<U extends SelectableChannel> extends GunCoreEventLoop {
    /**
     * queue
     * push to queue
     */
    void push(U u);

    /**
     * loop the queue
     */
    @Override
    void loop();


    @Override
    default int init(ExecutorService deal, GunNettyPipeline pipeline) {
        return 0;
    }
}
