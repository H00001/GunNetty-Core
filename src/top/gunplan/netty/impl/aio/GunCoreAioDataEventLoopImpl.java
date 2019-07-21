package top.gunplan.netty.impl.aio;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.impl.GunNettyCoreThreadManager;

import java.nio.channels.SelectionKey;
import java.util.Set;

public class GunCoreAioDataEventLoopImpl extends AbstractGunCoreAioEventLoop {

    @Override
    public void run() {

    }

    @Override
    public <V extends GunCoreEventLoop> V registerManager(GunNettyCoreThreadManager manager) {
        return null;
    }

    @Override
    public Set<SelectionKey> availableSelectionKey() {
        return null;
    }

    @Override
    public void startEventLoop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void stopEventLoop() {

    }
}
