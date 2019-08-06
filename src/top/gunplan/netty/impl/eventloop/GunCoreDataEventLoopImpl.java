/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunNettyPipeline;

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
    private AtomicInteger listenSize = new AtomicInteger(0);
    private final int timeWait;


    GunCoreDataEventLoopImpl() {
        timeWait = GUN_NETTY_CORE_PROPERTY.getClientWaitTime();
    }

    private void continueLoop() {
        LockSupport.unpark(workThread);
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
    public void dealEvent(SelectionKey key) {
        key.interestOps(0);
        listenSize.decrementAndGet();
        this.deal.submit(new GunCoreCalculatorWorker(pipeline, key, listenSize));
    }

    @Override
    public int init(ExecutorService deal, GunNettyPipeline pipeline) throws IOException {
        return super.init(deal, pipeline);
    }


    @Override
    public Set<SelectionKey> availableSelectionKey() {
        bootSelector.wakeup();
        return bootSelector.keys();
    }
}
