package top.gunplan.netty.impl.aio;

import sun.nio.ch.BsdAsynchronousChannelProvider;
import top.gunplan.netty.GunNettyPipeline;

import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;


import java.nio.channels.spi.AsynchronousChannelProvider;

import java.util.concurrent.ExecutorService;

/**
 * GunCoreAioConnectionEventLoopImpl
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-09 10:35
 */
public class GunCoreAioConnectionEventLoopImpl extends AbstractGunCoreAioEventLoop {
    private final AsynchronousServerSocketChannel var57;
    private AsynchronousChannelProvider provider = new BsdAsynchronousChannelProvider();
    private GunNettyPipeline pipeline;

    GunCoreAioConnectionEventLoopImpl(ExecutorService deal, GunNettyPipeline dealHandle, int port) throws IOException {
        this.pipeline = dealHandle;
        var57 = provider.openAsynchronousServerSocketChannel(provider.openAsynchronousChannelGroup(deal, 10));
        try {
            var57.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            AbstractGunBaseLogUtil.urgency(e.getMessage());
        }

    }

    @Override
    public synchronized void run() {

//        try {
//           var57.accept()
//
//        } catch (Exception exp) {
//            AbstractGunBaseLogUtil.error(exp.getMessage());
//        }


    }
}
