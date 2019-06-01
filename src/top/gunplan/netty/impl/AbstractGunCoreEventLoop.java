package top.gunplan.netty.impl;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.common.GunNettyPropertyManagerImpl;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * AbstractGunCoreEventLoop
 * @see GunCoreDataEventLoop
 * @see GunCoreConnectionEventLoop
 * @author dosdrtt
 */
public abstract class AbstractGunCoreEventLoop implements Runnable, GunCoreEventLoop {
    Selector bootSelector;
    ExecutorService deal;
    final GunNettyCoreProperty coreProperty = GunNettyPropertyManagerImpl.coreProperty();

    AbstractGunCoreEventLoop(ExecutorService deal) throws IOException {
        bootSelector = Selector.open();
        this.deal = deal;
    }

    /**
     * get all of avaliable keys
     * async invoke
     */
    public Set<SelectionKey> getAvaliableSelectionKey() {
        return bootSelector.keys();
    }
    /**
     * @param key SelectionKey
     * @throws Exception unKnown Exception
     */
    @Override
    public abstract void dealEvent(SelectionKey key) throws Exception;
}



