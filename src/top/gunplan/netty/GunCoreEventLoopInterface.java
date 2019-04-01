package top.gunplan.netty;

import java.nio.channels.SelectionKey;

/**
 *
 */
public interface GunCoreEventLoopInterface {
    /**
     *
     * @param key SelectionKey
     * @throws Exception SelectionKey
     */
    void dealEvent(SelectionKey key) throws Exception;
}
