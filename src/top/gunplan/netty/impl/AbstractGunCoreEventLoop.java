package top.gunplan.netty.impl;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettySystemServices;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;


import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * AbstractGunCoreEventLoop
 *
 * @author dosdrtt
 * @see GunCoreDataEventLoop
 * @see GunCoreConnectionEventLoop
 */
public abstract class AbstractGunCoreEventLoop implements Runnable, GunCoreEventLoop {
    final ExecutorService deal;
    volatile Selector bootSelector;
    final GunNettyCoreProperty coreProperty = GunNettySystemServices.coreProperty();

    AbstractGunCoreEventLoop(ExecutorService deal) throws IOException {
        bootSelector = Selector.open();
        this.deal = deal;
    }

    /**
     * get all of avaliable keys
     * async invoke
     */
    Set<SelectionKey> getAvailableSelectionKey() {
        bootSelector.wakeup();
        return bootSelector.keys();
    }

    /**
     * dealEvent
     *
     * @param key SelectionKey
     * @throws Exception unKnown Exception
     */
    @Override
    public abstract void dealEvent(SelectionKey key) throws Exception;
}



