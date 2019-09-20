package top.gunplan.netty.impl.eventloop;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * AbstractGunCoreAioEventLoop
 *
 * @author frank albert
 * @version 0.0.0.1
 * # 2019-06-09 10:25
 */

public abstract class AbstractGunCoreAioEventLoop implements GunConnEventLoop {
    private volatile AsynchronousServerSocketChannel serverSocketChannel;

    public AbstractGunCoreAioEventLoop() {
        super();
    }


    @Override
    public void loop() {

    }

    @Override
    public int init(ExecutorService deal) throws IOException {
        serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        return serverSocketChannel.isOpen() ? 0 : -1;
    }


    /**
     * aio running method
     */


    @Override
    public void dealEvent(SelectionKey key) throws Exception {
        Future<AsynchronousSocketChannel> channelFuture = serverSocketChannel.accept();
        // channelFuture.get().read()
    }


    @Override
    public boolean isLoopNext() {
        return isRunning();
    }


    @Override
    public int openPort(int... port) throws IOException {
        serverSocketChannel.bind(new InetSocketAddress(port[0]));
        return 0;
    }
}