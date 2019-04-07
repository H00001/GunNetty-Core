package top.gunplan.netty.impl;
import top.gunplan.netty.GunPilelineInterface;
import top.gunplan.utils.AbstractGunBaseLogUtil;
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
    private final GunPilelineInterface dealHandle;

    CunCoreConnetcionThread(ExecutorService deal, GunPilelineInterface dealHandle, int port) throws IOException {
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
        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
        CunCoreDataEventLoop selectionThread = ((CunCoreDataEventLoop) CoreThreadManage.getDealThread());
        socketChannel.socket().setTcpNoDelay(true);
        selectionThread.registerReadKey(socketChannel);
        selectionThread.incrAndContinueLoop();
        this.deal.submit(new GunAcceptWorker(dealHandle, socketChannel));
    }
}
