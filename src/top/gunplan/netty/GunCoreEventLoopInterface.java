package top.gunplan.netty;

import java.nio.channels.SelectionKey;

/**
 *
 */
public interface GunCoreEventLoopInterface {
    /**
     *
     * @param key
     * @throws Exception
     */
    void dealEvent(SelectionKey key) throws Exception;
}
