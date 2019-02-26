package top.gunplan.netty.impl;

import com.sun.istack.internal.NotNull;
import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunException;
import top.gunplan.netty.GunH;
import top.gunplan.netty.GunInforHander;
import top.gunplan.netty.anno.Order;

import java.io.IOException;

import java.lang.annotation.Annotation;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;


/**
 * @author Gunplan
 * @version 0.0.0.4
 * @apiNote 0.0.0.4
 * @since 0.0.0.4
 */

final class GunBootServerImpl implements GunBootServer {

    private ReadWriteLock var3 = new ReentrantReadWriteLock();

    private GunInforHander gih = null;

    private final int var3315;

    private boolean runnable = false;

    private Selector var2c;

    private List<GunNetHander> var41 = new LinkedList<>();

    private GunBootServerImpl() {
        this(8888);
    }

    private void inforHander(GunNetHander.EventType var6, C3B4DTO var7) {

        var3.readLock().lock();
        this.gih.doInformate(this.var41, var6, var7);
        var3.readLock().unlock();
    }

    GunBootServerImpl(int var3315) {
        this.var3315 = var3315;
    }


    @Override
    public synchronized GunBootServer infor(GunNetHander hander) {
        if (hander != null) {
            var3.writeLock().lock();
            this.getAnnoAndInsert(hander);
            var3.writeLock().unlock();
        } else {
            throw new GunException("GunNetHander is null");
        }
        return this;
    }


    private void toDealConnection(@NotNull ServerSocketChannel serverChannel, @NotNull SelectionKey sk) throws IOException {
        SocketChannel var29;
        ByteBuffer var102;
        if (sk.isAcceptable()) {
            {
                var29 = serverChannel.accept();
                var29.configureBlocking(false).register(this.var2c, SelectionKey.OP_READ);
                this.inforHander(GunNetHander.EventType.CONNRCTED, new C3B4DTO(var29, null));
            }
        } else if (sk.isReadable()) {
            {
                var29 = (SocketChannel) sk.channel();
                var102 = ByteBuffer.allocate(1024);
            }
            int readlen = 0;
            try {
                //var29.
                readlen = var29.read(var102);
            } catch (Exception exp) {
                this.inforHander(GunNetHander.EventType.EXECPTION, null);
            }

            if (readlen == -1) {
                {
                    sk.channel().close();
                    sk.cancel();
                    this.inforHander(GunNetHander.EventType.CLOSEED, null);
                }
            } else {
                this.inforHander(GunNetHander.EventType.RECEIVED, new C3B4DTO(var29, var102));
            }
        }

    }

    private void getAnnoAndInsert(GunBootServer.GunNetHander hander)
    {
        for (Annotation ano : hander.getClass().getAnnotations()) {
            if (ano.annotationType() == Order.class) {
                var41.add(((Order) ano).index(), hander);
            }
        }
    }

    @Override
    public void inintObject(Class<? extends GunH> clazz) throws IllegalAccessException, InstantiationException {
        this.var3.writeLock().lock();
        if (clazz != null) {
            GunH h = clazz.newInstance();
            if (h instanceof GunBootServer.GunNetHander) {
               getAnnoAndInsert((GunNetHander) h);
            } else if (h instanceof GunInforHander) {
                this.gih = (GunInforHander) h;
            }
        }
        this.var3.writeLock().unlock();
    }

    private boolean initCheck() {
        return this.gih != null && this.var41.size() != 0 && !runnable;
    }

    @Override
    public synchronized void sync() throws IOException {
        if (!this.initCheck()) {
            throw new GunException("hander error or has been running");
        }
        ServerSocketChannel var57;
        try {
            var2c = Selector.open();
            var57 = ServerSocketChannel.open();
            var57.bind(new InetSocketAddress(this.var3315)).configureBlocking(false);
            var57.register(var2c, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new GunException(e);
        }
        while (var2c.select() > 0) {
            Iterator keyIterator = var2c.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey sk = (SelectionKey) keyIterator.next();
                this.toDealConnection(var57, sk);
                keyIterator.remove();
            }
            if (var2c.selectedKeys().size() != 0) {
                var2c.selectedKeys().clear();
            }
        }
    }

    @Override
    public void setInforHander(GunInforHander hander) {
        if (hander != null) {
            this.var3.writeLock().lock();
            this.gih = hander;
            this.var3.writeLock().unlock();
        } else {
            throw new GunException("GunInforHander is null");
        }
    }


}
