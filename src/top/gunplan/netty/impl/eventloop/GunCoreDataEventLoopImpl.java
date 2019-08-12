/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunException;
import top.gunplan.netty.impl.GunNettyChildChannel;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * this class used to loop to get the event like accept or read
 *
 * @author dosdrtt
 */
class GunCoreDataEventLoopImpl extends AbstractGunCoreEventLoop implements GunDataEventLoop<SocketChannel> {
    private final AtomicInteger listenSize = new AtomicInteger(0);
    private final int timeWait;


    GunCoreDataEventLoopImpl() {
        timeWait = GUN_NETTY_CORE_PROPERTY.getClientWaitTime();
    }

    private void continueLoop() {
        LockSupport.unpark(workThread);
    }

    @Override
    public Set<SelectionKey> availableSelectionKey() throws IOException {
        //fast release
        fastLimit();
        return bootSelector.keys();
    }

    @Override
    public void incrAndContinueLoop() {
        listenSize.incrementAndGet();
        continueLoop();

    }


    @Override
    public void decreaseAndStop() {
        listenSize.decrementAndGet();
    }

    @Override
    public SelectionKey registerReadKey(SocketChannel channel) throws IOException {
        final SelectionKey key = channel.register(this.bootSelector, SelectionKey.OP_READ, this);
        this.incrAndContinueLoop();
        return key;
    }


    @Override
    public void nextDeal() {
        if (listenSize.get() == 0) {
            LockSupport.park();
        }
        try {
            int val = timeWait == -1 ? bootSelector.select() : bootSelector.select(timeWait);
            if (val > 0) {
                Iterator<SelectionKey> keyIterator = bootSelector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    final SelectionKey sk = keyIterator.next();
                    this.dealEvent(sk);
                    keyIterator.remove();
                }
            }
            bootSelector.selectNow();
        } catch (IOException exp) {
            throwGunException(exp);
        }
    }


    private void throwGunException(IOException e) throws GunException {
        throw new GunException(e);
    }


    @Override
    @SuppressWarnings("unchecked")
    public void dealEvent(SelectionKey key) {
        key.interestOps(0);
        listenSize.decrementAndGet();
        this.deal.submit(new GunCoreCalculatorWorker((GunNettyChildChannel<SocketChannel>) key.attachment(), key, listenSize));
    }

    @Override
    public int init(ExecutorService deal) throws IOException {
        return super.init(deal);
    }


    @Override
    public int fastLimit() throws IOException {
        bootSelector.wakeup();
        return bootSelector.select(0);
    }
}
