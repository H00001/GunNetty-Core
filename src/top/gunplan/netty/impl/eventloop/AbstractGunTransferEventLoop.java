package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.impl.GunNettyCoreThreadManager;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.util.concurrent.ExecutorService;

/**
 * AbstractGunTransferEventLoop
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-23 00:36
 */
public abstract class AbstractGunTransferEventLoop<U extends SelectableChannel> implements GunNettyTransfer<U> {
    private boolean running = false;
    volatile GunNettyCoreThreadManager manager;

    @Override
    public void init(ExecutorService deal, GunNettyPipeline pipeline) throws IOException {

    }


    @SuppressWarnings("unchecked")
    @Override
    public GunNettyTransfer registerManager(GunNettyCoreThreadManager manager) {
        this.manager = manager;
        return this;
    }


    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void startEventLoop() {
        running = true;
    }

    @Override
    public void stopEventLoop() {
        running = false;
    }
}
