package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutorService;


/**
 * AbstractGunCoreAioEventLoop
 *
 * @author frank albert
 * @version 0.0.0.1
 * # 2019-06-09 10:25
 */

public abstract class AbstractGunCoreAioConnectionEventLoop implements GunConnEventLoop, CompletionHandler<AsynchronousSocketChannel, Object> {
    private volatile AsynchronousServerSocketChannel serverSocketChannel;
    private volatile boolean isRunning = false;

    AbstractGunCoreAioConnectionEventLoop() {
        super();
    }

    @Override
    public void loop() {
        serverSocketChannel.accept(null, this);
    }

    @Override
    public int init(ExecutorService deal) throws IOException {
        serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        return serverSocketChannel.isOpen() ? 0 : -1;
    }

    @Override
    public void startEventLoop() {

    }

    /**
     * aio running method
     */
    @Override
    public void dealEvent(SelectionKey key) throws Exception {
        throw new GunException(GunExceptionType.NOT_SUPPORT, "AIO Not support this method");
    }


    @Override
    public boolean isLoopNext() {
        return isRunning();
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public int openPort(int... port) throws IOException {
        serverSocketChannel.bind(new InetSocketAddress(port[0]));
        return 0;
    }
}