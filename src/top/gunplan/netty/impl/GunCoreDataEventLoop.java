package top.gunplan.netty.impl;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunPipeline;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.Iterator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * this class used to loop to get the event like accept or read
 *
 * @author dosdrtt
 */
public class GunCoreDataEventLoop extends AbstractGunCoreEventLoop {
    private final GunPipeline pipeline;
    private AtomicInteger listenSize = new AtomicInteger(0);
    private volatile Thread nowRun = null;


    public void continueLoop() {
        LockSupport.unpark(nowRun);
    }

    void incrAndContinueLoop() {
        listenSize.incrementAndGet();
        LockSupport.unpark(nowRun);
    }

    GunCoreDataEventLoop(ExecutorService deal, final GunPipeline pileline) throws IOException {
        super(deal);
        this.pipeline = pileline;

    }

    SelectionKey registerReadKey(SelectableChannel channel) throws IOException {
        final SelectionKey key = channel.register(this.bootSelector, SelectionKey.OP_READ, this);
        this.incrAndContinueLoop();
        return key;
    }


    @Override
    public synchronized void run() {
        try {
            nowRun = Thread.currentThread();
            while (CoreThreadManage.status) {

                if (listenSize.get() == 0) {
                    LockSupport.park();
                }
                assert coreProperty != null;
                int val = coreProperty.getClientWaitTime() == -1 ? bootSelector.select() : bootSelector.select(coreProperty.getClientWaitTime());
                if (val > 0) {
                    Iterator<SelectionKey> keyIterator = bootSelector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        final SelectionKey sk = keyIterator.next();
                        this.dealEvent(sk);
                        keyIterator.remove();
                    }

                }
                bootSelector.selectNow();
            }
            bootSelector.close();
        } catch (Exception exp) {
            throw new GunException(exp);
        }
    }


    @Override
    public void dealEvent(SelectionKey key) {
        key.interestOps(0);
        listenSize.decrementAndGet();
        this.deal.submit(new GunCoreCalculatorWorker(pipeline, key, listenSize));
    }

}
