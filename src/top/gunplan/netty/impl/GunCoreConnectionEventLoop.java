package top.gunplan.netty.impl;

import top.gunplan.netty.GunPipeline;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

/**
 * GunCoreConnectionEventLoop deal connection event
 *
 * @author dosdrtt
 * @see AbstractGunCoreEventLoop
 */
public class GunCoreConnectionEventLoop extends AbstractGunCoreEventLoop {
    private final GunPipeline dealHandle;

    GunCoreConnectionEventLoop(ExecutorService deal, GunPipeline dealHandle, int port) throws IOException {
        super(deal);
        this.dealHandle = dealHandle;
        try {
            ServerSocketChannel var57 = ServerSocketChannel.open();
            var57.bind(new InetSocketAddress(port)).configureBlocking(false);
            var57.register(bootSelector, SelectionKey.OP_ACCEPT, this);
        } catch (IOException e) {
            AbstractGunBaseLogUtil.urgency(e.getMessage());
        }

    }

    @Override
    public synchronized void run() {
        try {
            while (bootSelector.select() > 0) {
                Iterator keyIterator = bootSelector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey sk = (SelectionKey) keyIterator.next();
                    this.dealEvent(sk);
                    keyIterator.remove();
                }
            }
        } catch (Exception exp) {
            AbstractGunBaseLogUtil.error(exp.getMessage());
        }

    }

    @Override
    public void dealEvent(SelectionKey key) {
        try {
            final SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
            GunCoreDataEventLoop selectionThread = ((GunCoreDataEventLoop) CoreThreadManage.getDealThread());
            socketChannel.socket().setTcpNoDelay(true);
            selectionThread.registerReadKey(socketChannel);
            selectionThread.incrAndContinueLoop();
            this.deal.submit(new GunAcceptWorker(dealHandle, socketChannel));

        } catch (Exception exp) {
            AbstractGunBaseLogUtil.error(exp.getMessage());
        }
    }
}
