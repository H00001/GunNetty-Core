package top.gunplan.netty.impl;

import com.sun.istack.internal.NotNull;
import top.gunplan.netty.*;
import top.gunplan.netty.anno.GunNetFilterOrder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.*;
import java.util.Iterator;


/**
 * @author Gunplan
 * @version 0.0.0.6
 * @apiNote 0.0.0.5
 * @since 0.0.0.4
 */

final class GunBootServerImpl implements GunBootServer {

    private final int var3315;

    private volatile boolean runnable = false;

    private Selector bootSelector;

    private ExecutorService acceptExector;

    private ExecutorService requestExector;

    private volatile GunNetHandle dealhander = null;

    private final List<GunNettyFilter> filters = new CopyOnWriteArrayList<>();

    private GunBootServerImpl() {
        this(8888);
    }

    @Override
    public boolean isRunnable() {
        return this.runnable;
    }


    GunBootServerImpl(int var3315) {
        this.var3315 = var3315;
    }


    @Override
    public synchronized GunBootServer setHandel(GunNetHandle handel) {
        if (handel != null) {
            this.getAnnoAndInsert(handel);
        } else {
            throw new GunException("GunNetHandle is null");
        }
        return this;
    }

    @Override
    public GunBootServer addFilter(GunNettyFilter filter) {
        GunNetFilterOrder order = filter.getClass().getAnnotation(GunNetFilterOrder.class);
        this.filters.add(order.index(), filter);
        return this;
    }


    private void toDealConnection(@NotNull SelectionKey sk) throws IOException {
        if (sk.isAcceptable()) {
            SocketChannel socketChannel = ((ServerSocketChannel) sk.channel()).accept();
            socketChannel.configureBlocking(false).register(this.bootSelector, SelectionKey.OP_READ);
            this.acceptExector.execute(new GunAcceptWorker(dealhander, socketChannel));
        } else if (sk.isReadable()) {
            this.acceptExector.submit(new GunCoreWorker(filters, dealhander, (SocketChannel) sk.channel()));
            sk.cancel();
        }
    }

    private void getAnnoAndInsert(GunNetHandle hander) {
        this.dealhander = hander;
    }

    @Override
    public void inintObject(Class<? extends GunHandle> clazz) throws IllegalAccessException, InstantiationException {
        if (clazz != null) {
            GunHandle h = clazz.newInstance();
            if (h instanceof GunNetHandle) {
                getAnnoAndInsert((GunNetHandle) h);
            }
        }
    }

    private boolean initCheck() {
        return this.acceptExector != null && requestExector != null && this.dealhander != null && !runnable;
    }

    @Override
    public synchronized void sync() throws IOException {
        if (!this.initCheck()) {
            throw new GunException("handel error or has been running");
        }
        try {
            ServerSocketChannel var57 = ServerSocketChannel.open();
            this.bootSelector = Selector.open();
            var57.bind(new InetSocketAddress(this.var3315)).configureBlocking(false);
            var57.register(bootSelector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new GunException(e);
        }

        while (bootSelector.select() > 0) {
            Iterator keyIterator = bootSelector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey sk = (SelectionKey) keyIterator.next();
                this.toDealConnection(sk);
                keyIterator.remove();
            }
        }
    }


    @Override
    public GunBootServer setExecuters(ExecutorService acceptExecuters, ExecutorService requestExecuters) {
        this.acceptExector = acceptExecuters;
        this.requestExector = requestExecuters;
        return this;
    }


}
