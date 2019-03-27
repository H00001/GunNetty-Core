package top.gunplan.netty.impl;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunCoreCalculatorWorker;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.nio.utils.GunBaseLogUtil;
import top.gunplan.nio.utils.GunBytesUtil;

import java.io.IOException;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author dosdrtt
 */
public class CunCoreDataThread extends AbstractGunCoreThread {
    private final List<GunNettyFilter> filters;
    private final GunBootServer.GunNetHandle dealHandle;
    private AtomicInteger listionSize = new AtomicInteger(0);

    private volatile Thread nowRun = null;

    public void contionue() {
        LockSupport.unpark(nowRun);
    }

    public CunCoreDataThread(ExecutorService deal, final List<GunNettyFilter> filters, final GunBootServer.GunNetHandle dealHandle) throws IOException {
        super(deal);
        this.filters = filters;
        this.dealHandle = dealHandle;
    }

    public void registerKey(SelectableChannel channel) throws IOException {
        channel.configureBlocking(false);
        listionSize.incrementAndGet();
        channel.register(this.bootSelector, SelectionKey.OP_READ);
    }


    @Override
    public synchronized void run() {
        try {

            nowRun = Thread.currentThread();
            nowRun.setName("DataThread");
            while (true) {

                if (listionSize.get() == 0) {
                    LockSupport.park();
                }
                int val = bootSelector.select(1000);
                if (val > 0) {
                    Iterator keyIterator = bootSelector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey sk = (SelectionKey) keyIterator.next();
                        this.dealEvent(sk);
                        keyIterator.remove();
                    }
                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }


    @Override
    public void dealEvent(SelectionKey key) {
        byte[] readbata;
        if (key.isValid()) {
            try {
                readbata = GunBytesUtil.readFromChannel((SocketChannel) key.channel(), 1024);
            } catch (IOException e) {
                listionSize.decrementAndGet();
                key.cancel();
                return;
            }
            if (readbata == null) {
                listionSize.decrementAndGet();
                GunBaseLogUtil.error("Client closed");
                key.cancel();
            } else {
                this.deal.submit(new GunCoreCalculatorWorker(filters, dealHandle, (SocketChannel) key.channel(), readbata));
            }
        }

    }
}
