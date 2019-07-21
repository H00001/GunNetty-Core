package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.impl.GunNettyCoreThreadManager;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.HashSet;
import java.util.Set;


public abstract class AbstractGunTransferEventLoop<U extends SelectableChannel> implements GunNettyTransfer<U> {
    private boolean running = false;
    volatile GunNettyCoreThreadManager manager;


    @Override
    public Set<SelectionKey> availableSelectionKey() {
        return new HashSet<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public GunNettyTransfer registerManager(GunNettyCoreThreadManager manager) {
        this.manager = manager;
        return this;
    }

    @Override
    public void run() {
        startEventLoop();
        loop();
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
