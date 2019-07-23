package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.GunNettySystemServices;
import top.gunplan.netty.impl.GunNettyCoreThreadManager;
import top.gunplan.netty.impl.propertys.GunNettyCoreProperty;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.ExecutorService;

/**
 * AbstractGunCoreEventLoop
 *
 * @author dosdrtt
 * @see GunCoreDataEventLoopImpl
 * @see GunCoreConnectionEventLoopImpl
 */
public abstract class AbstractGunCoreEventLoop implements Runnable, GunCoreEventLoop {
    volatile ExecutorService deal;
    volatile Selector bootSelector;
    volatile GunNettyPipeline pipeline;
    volatile boolean running;
    volatile Thread workThread;
    final GunNettyCoreProperty coreProperty = GunNettySystemServices.coreProperty();
    GunNettyCoreThreadManager manager;

    AbstractGunCoreEventLoop() {

    }

    @Override
    public void init(ExecutorService deal, final GunNettyPipeline pipeline) throws IOException {
        this.deal = deal;
        this.pipeline = pipeline;
        bootSelector = Selector.open();
    }

    @SuppressWarnings("unchecked")
    @Override
    public GunCoreEventLoop registerManager(GunNettyCoreThreadManager manager) {
        this.manager = manager;
        return this;
    }


    /**
     * dealEvent
     *
     * @param key SelectionKey
     * @throws Exception unKnown Exception
     */
    @Override
    public abstract void dealEvent(SelectionKey key) throws Exception;


    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void startEventLoop() {
        workThread = Thread.currentThread();
        this.running = true;
    }

    @Override
    public void stopEventLoop() {
        this.running = false;
    }
}



