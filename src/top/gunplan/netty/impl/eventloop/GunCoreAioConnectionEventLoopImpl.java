package top.gunplan.netty.impl.eventloop;


import top.gunplan.netty.GunCoreEventLoop;
import top.gunplan.netty.impl.GunNettyEventLoopManager;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Selector;

/**
 * GunCoreAioConnectionEventLoopImpl
 *
 * @author frank albert
 * @version 0.0.0.1
 */
public class GunCoreAioConnectionEventLoopImpl extends AbstractGunCoreAioConnectionEventLoop {



    @Override
    public void stopEventLoop() {

    }


    @Override
    public int listenPort() {
        return 0;
    }

    @Override
    public Selector select() {
        return null;
    }

    @Override
    public GunCoreEventLoop registerManager(GunNettyEventLoopManager manager) {
        return null;
    }

    @Override
    public void completed(AsynchronousSocketChannel result, Object attachment) {
    }

    @Override
    public void failed(Throwable exc, Object attachment) {

    }
}
