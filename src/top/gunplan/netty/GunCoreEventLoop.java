package top.gunplan.netty;

import top.gunplan.netty.impl.GunNettyManagerGetter;
import top.gunplan.netty.impl.eventloop.GunNettyVariableWorker;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutorService;

/**
 * GunCoreEventLoop event loop deal class
 * when event happened, function `dealEvent` will be
 * execute
 *
 * @author dosdrtt
 */

public interface GunCoreEventLoop extends Runnable, GunNettyVariableWorker, GunNettyManagerGetter<GunCoreEventLoop> {
    /**
     * when the event come ,the method will be call back
     *
     * @param key SelectionKey input the SelectionKey
     * @throws Exception Kinds of Exception
     */
    void dealEvent(SelectionKey key) throws Exception;


    /**
     * running on new thread not main
     */
    @Override
    default void run() {
        startEventLoop();
        loop();
    }


    void loop();


    /**
     * available channels
     */
    void init(ExecutorService deal, final GunNettyPipeline pipeline) throws IOException;
}
