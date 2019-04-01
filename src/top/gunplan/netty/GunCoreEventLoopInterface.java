package top.gunplan.netty;

import java.nio.channels.SelectionKey;

/**
 *GunCoreEventLoopInterface event loop deal class
 */
public interface GunCoreEventLoopInterface {
    /**
     * when the event come ,the method will be call back
     * @param key SelectionKey input the SelectionKey
     * @throws Exception SelectionKey
     */
    void dealEvent(SelectionKey key) throws Exception;
}
