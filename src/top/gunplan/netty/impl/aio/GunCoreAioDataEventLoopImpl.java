package top.gunplan.netty.impl.aio;

import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.impl.GunNettyCoreThreadManager;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class GunCoreAioDataEventLoopImpl extends AbstractGunCoreAioEventLoop {

    @Override
    public void run() {

    }

    @Override
    public void loop() {

    }

    @Override
    public <V extends GunCoreEventLoop> V registerManager(GunNettyCoreThreadManager manager) {
        return null;
    }

    @Override
    public void init(ExecutorService deal, GunNettyPipeline pipeline) throws IOException {

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
