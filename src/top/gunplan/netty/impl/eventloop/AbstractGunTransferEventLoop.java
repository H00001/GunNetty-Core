package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.impl.GunNettyCoreThreadManager;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * AbstractGunTransferEventLoop
 *
 * @author frank albert
 * @version 0.0.2.1
 * @date 2019-07-23 00:36
 */
public abstract class AbstractGunTransferEventLoop<U extends SelectableChannel> implements GunNettyTransfer<U> {
    private volatile boolean running = false;
    private volatile GunNettyCoreThreadManager manager;

    /**
     * do transfer
     */
    public abstract void loopTransfer();

    @Override
    public boolean isLoopNext() {
        return isRunning();
    }

    SelectionKey registerReadChannelToDataEventLoop(SocketChannel channel) throws IOException {
        channel.configureBlocking(false);
        GunDataEventLoop<SocketChannel> register = manager.dealChannelEventLoop();
        return register.registerReadKey(channel);
    }

    @Override
    public void dealEvent(SelectionKey socketChannel) throws IOException {
        SocketChannel channel = ((SocketChannel) socketChannel.channel());
        channel.socket().setTcpNoDelay(true);

    }

    @Override
    public GunNettyTransfer<U> registerManager(GunNettyCoreThreadManager manager) {
        this.manager = manager;
        return this;
    }


    @Override
    public void loop() {
        for (; isLoopNext(); ) {
            loopTransfer();
        }
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
