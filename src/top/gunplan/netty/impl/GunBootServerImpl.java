package top.gunplan.netty.impl;

import com.sun.istack.internal.NotNull;
import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunException;
import top.gunplan.netty.GunH;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;


/**
 * @author Gunplan
 * @version 0.0.0.5
 * @apiNote 0.0.0.4
 * @since 0.0.0.4
 */

final class GunBootServerImpl implements GunBootServer {

    private final int var3315;

    private volatile boolean runnable = false;

    private Selector var2c;

    private Executor acceptExector;

    private Executor requestExector;

    private volatile GunNetHander dealhander = null;

    private final List<GunNettyFilter> filters = new CopyOnWriteArrayList<>();

    private GunBootServerImpl() {
        this(8888);
    }

    @Override
    public boolean getRunnable() {
        return this.runnable;
    }


    GunBootServerImpl(int var3315) {
        this.var3315 = var3315;
    }


    @Override
    public synchronized GunBootServer infor(GunNetHander hander) {
        if (hander != null) {
            this.getAnnoAndInsert(hander);
        } else {
            throw new GunException("GunNetHander is null");
        }
        return this;
    }

    @Override
    public void addFilter(GunNettyFilter filter) {
        this.filters.add(filter);
    }


    private void toDealConnection(@NotNull SelectionKey sk) throws IOException {
        if (sk.isAcceptable()) {
            SocketChannel socketChannel = ((ServerSocketChannel) sk.channel()).accept();
            socketChannel.configureBlocking(false).register(this.var2c, SelectionKey.OP_READ);
            this.acceptExector.execute(new GunAcceptWorker(dealhander, socketChannel));
        } else if (sk.isReadable()) {
            this.acceptExector.execute(new GunRequestWorker(filters, dealhander, (SocketChannel) sk.channel()));
            sk.cancel();
        }
    }

    private void getAnnoAndInsert(GunBootServer.GunNetHander hander) {
        this.dealhander = hander;
    }

    @Override
    public void inintObject(Class<? extends GunH> clazz) throws IllegalAccessException, InstantiationException {
        if (clazz != null) {
            GunH h = clazz.newInstance();
            if (h instanceof GunBootServer.GunNetHander) {
                getAnnoAndInsert((GunNetHander) h);
            }
        }
    }

    private boolean initCheck() {
        return this.acceptExector != null && requestExector != null && this.dealhander != null && !runnable;
    }

    @Override
    public synchronized void sync() throws IOException {
        if (!this.initCheck()) {
            throw new GunException("hander error or has been running");
        }
        ServerSocketChannel var57;
        try {
            var57 = ServerSocketChannel.open();
            var2c = Selector.open();
            var57.bind(new InetSocketAddress(this.var3315)).configureBlocking(false);
            var57.register(var2c, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new GunException(e);
        }

        while (var2c.select() > 0) {
            Iterator keyIterator = var2c.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey sk = (SelectionKey) keyIterator.next();
                this.toDealConnection(sk);
                keyIterator.remove();
            }
        }
    }


    @Override
    public void setExecuters(Executor acceptExector, Executor requestExector) {
        this.acceptExector = acceptExector;
        this.requestExector = requestExector;
    }


}
