package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.common.GunNettyContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

/**
 * GunCoreConnectionEventLoopImpl deal connection event
 *
 * @author dosdrtt
 * @see AbstractGunCoreEventLoop
 */
public class GunCoreConnectionEventLoopImpl extends AbstractGunCoreEventLoop implements GunConnEventLoop {
    private final ServerSocketChannel var57;
    private volatile int port;

    GunCoreConnectionEventLoopImpl() throws IOException {
        var57 = ServerSocketChannel.open();
    }

    @Override
    public int openPort(int port) {
        this.port = port;
        return 0;
    }

    @Override
    public int listenPort() {
        return this.port;
    }


    @Override
    public void init(ExecutorService deal, GunNettyPipeline pipeline) throws IOException {
        super.init(deal, pipeline);
        var57.bind(new InetSocketAddress(port)).configureBlocking(false);
        var57.register(bootSelector, SelectionKey.OP_ACCEPT, this);
    }

    @Override
    public synchronized void loop() {
        try {
            while (bootSelector.select() > 0 && running) {
                Iterator keyIterator = bootSelector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey sk = (SelectionKey) keyIterator.next();
                    this.dealEvent(sk);
                    keyIterator.remove();
                }
            }
        } catch (IOException exp) {
            GunNettyContext.logger.error(exp.getMessage());
        }
    }

    @Override
    public void dealEvent(SelectionKey key) throws IOException {
        final SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
        manager.transferThread().push(socketChannel);
        BaseGunNettyWorker worker = new GunAcceptWorker(pipeline, socketChannel);
        if (worker.init() == 0) {
            this.deal.submit(worker);
        }
    }
}
