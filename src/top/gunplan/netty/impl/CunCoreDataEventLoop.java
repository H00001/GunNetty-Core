package top.gunplan.netty.impl;

import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunCoreCalculatorWorker;
import top.gunplan.netty.GunException;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.common.GunNettyProperty;
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
public class CunCoreDataEventLoop extends AbstractGunCoreEventLoop {
    private final List<GunNettyFilter> filters;
    private final GunBootServer.GunNetHandle dealHandle;
    private AtomicInteger listionSize = new AtomicInteger(0);
    private boolean runState = true;
    private volatile Thread nowRun = null;

    public void setRunState(boolean runState) {
        this.runState = runState;
    }

    void continueLoop() {
        LockSupport.unpark(nowRun);
    }

    CunCoreDataEventLoop(ExecutorService deal, final List<GunNettyFilter> filters, final GunBootServer.GunNetHandle dealHandle) throws IOException {
        super(deal);
        this.filters = filters;
        this.dealHandle = dealHandle;
    }

    void registerReadKey(SelectableChannel channel) throws IOException {
        channel.configureBlocking(false);
        listionSize.incrementAndGet();
        channel.register(this.bootSelector, SelectionKey.OP_READ);
    }


    @Override
    public synchronized void run() {
        try {
            nowRun = Thread.currentThread();
            nowRun.setName(this.getClass().getSimpleName());
            while (runState) {
                if (listionSize.get() == 0) {
                    LockSupport.park();
                }
                int val = bootSelector.select(GunNettyProperty.getClientWaitTime());
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
            throw new GunException(exp);
        }
    }


    @Override
    public void dealEvent(SelectionKey key) throws IOException {
        byte[] readbata;
        if (key.isValid()) {
            try {
                readbata = GunBytesUtil.readFromChannel((SocketChannel) key.channel(), GunNettyProperty.getFileReadBufferMin());
            } catch (IOException e) {
                listionSize.decrementAndGet();
                key.channel().close();
                key.cancel();
                return;
            }
            if (readbata == null) {
                listionSize.decrementAndGet();
                GunBaseLogUtil.debug("Client closed", "[CONNECTION]");
                key.channel().close();
                key.cancel();
            } else {
                this.deal.submit(new GunCoreCalculatorWorker(filters, dealHandle, (SocketChannel) key.channel(), readbata));
            }
        }

    }
}
