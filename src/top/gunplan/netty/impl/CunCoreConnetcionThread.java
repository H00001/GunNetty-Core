package top.gunplan.netty.impl;

import top.gunplan.netty.GunBootServer;
import top.gunplan.nio.utils.GunBaseLogUtil;
import java.io.IOException;
import java.net.InetSocketAddress;

import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

/**
 * @author dosdrtt
 */
public class CunCoreConnetcionThread extends AbstractGunCoreEventLoop {
    private final GunBootServer.GunNetHandle dealHandle;

    CunCoreConnetcionThread(ExecutorService deal, GunBootServer.GunNetHandle dealHandle, int port) throws IOException {
        super(deal);
        this.dealHandle = dealHandle;
        try {
            ServerSocketChannel var57 = ServerSocketChannel.open();
            var57.bind(new InetSocketAddress(port)).configureBlocking(false);
            var57.register(bootSelector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            GunBaseLogUtil.urgency(e.getMessage());
        }

    }

    @Override
    public synchronized void run() {
        try {
            Thread.currentThread().setName("accept Thread");
            while (bootSelector.select() > 0) {
                Iterator keyIterator = bootSelector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey sk = (SelectionKey) keyIterator.next();
                    this.dealEvent(sk);
                    keyIterator.remove();
                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }


    @Override
    public void dealEvent(SelectionKey key) throws Exception {
        GunBaseLogUtil.debug("connected....","[CONNECTION]");
        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
        CunCoreDataEventLoop selectionThread = ((CunCoreDataEventLoop) CoreThreadManage.getDealThread());
        selectionThread.registerReadKey(socketChannel);
        selectionThread.continueLoop();
        this.deal.submit(new GunBootServer.GunAcceptWorker(dealHandle, socketChannel));
    }
}
