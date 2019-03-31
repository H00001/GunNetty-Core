package top.gunplan.netty.impl;

import top.gunplan.netty.GunCoreEventLoopInterface;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.ExecutorService;

/**
 * @author dosdrtt
 */
public abstract class AbstractGunCoreEventLoop implements Runnable, GunCoreEventLoopInterface {
    Selector bootSelector;
    ExecutorService deal;

    AbstractGunCoreEventLoop(ExecutorService deal) throws IOException {
        bootSelector = Selector.open();
        this.deal = deal;
    }

    /**
     * @param key SelectionKey
     * @throws Exception unKnown Exception
     */
    @Override
    public abstract void dealEvent(SelectionKey key) throws Exception;
}



