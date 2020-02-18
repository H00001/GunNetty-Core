/*
 * Copyright (c) frankHan personal 2017-2018
 */

package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunException;
import top.gunplan.netty.GunExceptionMode;
import top.gunplan.netty.impl.channel.GunNettyChildChannel;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * this class used to loop to get the event like accept or read
 *
 * @author dosdrtt
 */
class GunCoreDataEventLoopImpl extends AbstractGunCoreEventLoop implements GunDataEventLoop<SocketChannel> {
    private final int timeWait;
    private final AtomicInteger listenSize = new AtomicInteger(0);
    private final Queue<RegisterFuture> waitToRegisterQueue = new ConcurrentLinkedDeque<>();

    @Override
    public Future<SelectionKey> registerReadKey(SocketChannel channel, Object attach) {
        //use async way to register channel
        var register = new RegisterFuture(new RegisterConstruct(channel, attach));
        waitToRegisterQueue.add(register);
        //make it continue rather than at WAIT status
        this.incrAndContinueLoop();
        //wake up selector if it is blocking select
        bootSelector.wakeup();
        return register;
    }

    private void method0() throws IOException {
        if ((timeWait == -1 ? bootSelector.select() : bootSelector.select(timeWait)) > 0) {
            Iterator<SelectionKey> keyIterator = bootSelector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                final SelectionKey sk = keyIterator.next();
                this.dealEvent(sk);
                keyIterator.remove();
            }
        } else {
            bootSelector.selectNow();
        }
        RegisterFuture r;
        while ((r = waitToRegisterQueue.poll()) != null) {
            r.key = r.construct.channel.register(this.bootSelector, SelectionKey.OP_READ, r.construct.attachment);
            r.unLock();
        }
    }


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
    public int fastLimit() {
        bootSelector.wakeup();
        return 0;
    }

    @Override
    public <CH extends SelectableChannel> void childChannelReadComplete(GunNettyChildChannel<CH> channel) {
        channel.key().interestOps(SelectionKey.OP_READ);
        this.incrAndContinueLoop();
    }

    @Override
    public void nextDeal() {
        if (listenSize.get() == 0) {
            LockSupport.park();
            if (listenSize.get() == 0) {
                return;
            }
        }
        try {
            method0();
        } catch (IOException exp) {
            throwGunException(exp);
        }
    }

    class RegisterFuture implements Future<SelectionKey> {
        final RegisterConstruct construct;
        volatile SelectionKey key;
        volatile Thread wait;
        volatile boolean isCancel = false;

        RegisterFuture(RegisterConstruct construct) {
            this.construct = construct;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            waitToRegisterQueue.remove(this);
            return isCancel = true;
        }

        @Override
        public boolean isCancelled() {
            return isCancel;
        }

        @Override
        public boolean isDone() {
            return key != null;
        }

        void unLock() {
            if (wait != null) {           // 5
                LockSupport.unpark(wait); // 6
            }
        }


        //this code is thread safe
        //stand 1 2 3 5 6 5 4
        //stand 5 6 1 4
        //special 1 2 5 6 3 4 :this process do not have thread safe problem

        @Override
        public SelectionKey get() {
            wait = Thread.currentThread(); // 1
            if (key == null) {             // 2
                LockSupport.park();        // 3
            }
            return key;                    // 4
        }

        @Override
        public SelectionKey get(long timeout, TimeUnit unit) {
            throw new GunException(GunExceptionMode.NOT_SUPPORT, "this method not support");
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
        this.submit(new GunCoreCalculatorWorker((GunNettyChildChannel<SocketChannel>) key.attachment()));
    }

    @Override
    public int init(ExecutorService deal) throws IOException {
        return super.init(deal);
    }

    public static class RegisterConstruct {
        final SocketChannel channel;
        final Object attachment;

        RegisterConstruct(SocketChannel channel, Object attachment) {
            this.channel = channel;
            this.attachment = attachment;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void stopEventLoop() {
        super.stopEventLoop();
        bootSelector.keys().parallelStream().forEach(channel -> ((GunNettyChildChannel<SocketChannel>) channel.attachment()).destroy());
        LockSupport.unpark(workThread);
        try {
            Thread.sleep(100);
            bootSelector.close();
        } catch (InterruptedException | IOException ignore) {

        }
    }
}
