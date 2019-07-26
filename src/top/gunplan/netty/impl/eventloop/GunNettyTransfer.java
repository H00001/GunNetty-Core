package top.gunplan.netty.impl.eventloop;


import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.impl.GunNettyChannelTransfer;

import java.nio.channels.Channel;
import java.util.concurrent.ExecutorService;

/**
 * GunNettyTransfer
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-06-19 00:28
 */

public interface GunNettyTransfer<U extends Channel> extends GunCoreEventLoop {
    /**
     * queue
     * push to queue
     */
    void push(GunNettyChannelTransfer<U> u);

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
