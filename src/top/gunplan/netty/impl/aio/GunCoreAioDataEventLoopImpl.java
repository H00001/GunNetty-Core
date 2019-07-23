package top.gunplan.netty.impl.aio;

import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.impl.GunNettyCoreThreadManager;
import top.gunplan.netty.impl.eventloop.GunConnEventLoop;

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

    @Override
    public GunConnEventLoop registerManager(GunNettyCoreThreadManager manager) {
        return null;
    }
}
