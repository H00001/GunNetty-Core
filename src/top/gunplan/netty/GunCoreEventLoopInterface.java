package top.gunplan.netty;

import java.nio.channels.SelectionKey;

/**
 * GunCoreEventLoopInterface event loop deal class
 * when event happened, function `dealEvent` will be
 * execute
 * @author dosdrtt
 */
public interface GunCoreEventLoopInterface {
    /**
     * when the event come ,the method will be call back
     * @param key SelectionKey input the SelectionKey
     * @throws Exception Kinds of Exception
     */
    void dealEvent(SelectionKey key) throws Exception;
}
