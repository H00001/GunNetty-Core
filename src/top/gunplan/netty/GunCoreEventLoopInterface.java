package top.gunplan.netty;

import java.nio.channels.SelectionKey;

public interface GunCoreEventLoopInterface {
    void dealEvent(SelectionKey key) throws Exception;
}
