package top.gunplan.netty.impl;

import com.sun.istack.internal.NotNull;
import top.gunplan.netty.GunBootServer;
import top.gunplan.netty.GunException;
import top.gunplan.netty.GunH;
import top.gunplan.netty.GunInforHander;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Gunplan
 * @version 0.0.0.3
 * @apiNote 0.0.0.2
 * @since 0.0.0.3
 */

final class GunBootServerimpl implements GunBootServer {

    private ReadWriteLock var3 = new ReentrantReadWriteLock();

    private volatile Executor var1 = null;

    private volatile Executor var2 = null;

    private GunInforHander gih = null;

    private final int var3315;

    private Selector var2c;

    private List<GunNetHander> var41 = new ArrayList<>(1);

    private GunBootServerimpl() {
        this(8888);
    }

    private void inforHander(GunNetHander.EventType var6, C3B4DTO var7) {
        var3.readLock().lock();
        this.gih.doInformate(this.var41, var6, var7);
        var3.readLock().unlock();
    }

    GunBootServerimpl(int var3315) {
        this.var3315 = var3315;
    }

    @Override
    public GunBootServer group(@NotNull Executor var8, @NotNull Executor var9) {
        if (this.var1 == null && this.var2 == null) {
            this.var1 = var8;
            this.var2 = var9;
        }
        return this;
    }

    @Override
    public synchronized GunBootServer infor(GunNetHander hander) {
        var3.writeLock().lock();
        var41.add(hander);
        var3.writeLock().unlock();
        return this;
    }


    private void toDealConnection(ServerSocketChannel serverChannel, SelectionKey sk) throws IOException {
        SocketChannel var29;
        ByteBuffer var102;
        if (sk.isAcceptable()) {
            var29 = serverChannel.accept();
            var29.configureBlocking(false).register(this.var2c, SelectionKey.OP_READ);
            this.inforHander(GunNetHander.EventType.CONNRCTED, new C3B4DTO(var29, null));
        } else if (sk.isReadable()) {
            var29 = (SocketChannel) sk.channel();
            var102 = ByteBuffer.allocate(1024);
            int readlen = 0;
            try {
                readlen = ((SocketChannel) sk.channel()).read(var102);
            } catch (Exception exp) {
                return;
            }
            if (readlen == -1) {
                sk.channel().close();
                sk.cancel();
                this.inforHander(GunNetHander.EventType.CLOSEED, null);
            } else {
                this.inforHander(GunNetHander.EventType.RECEIVED, new C3B4DTO(var29, var102));
            }
        }

    }

    @Override
    public void inintObject(Class<? extends GunH> clazz) throws IllegalAccessException, InstantiationException {
        this.var3.writeLock().lock();
        if (clazz != null) {
            GunH h = clazz.newInstance();
            if (h instanceof GunBootServer.GunNetHander) {
                this.var41.add((GunBootServer.GunNetHander) h);
            } else if (h instanceof GunInforHander) {
                this.gih = (GunInforHander) h;
            }
        }
        this.var3.writeLock().unlock();
    }


    @Override
    public void sync() throws Exception {
        ServerSocketChannel var57;
        try {
            var2c = Selector.open();
            var57 = ServerSocketChannel.open();
            var57.bind(new InetSocketAddress(this.var3315)).configureBlocking(false);
            var57.register(var2c, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new GunException(e);
        }
        for (; var2c.select() > 0; ) {
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
        this.var3.writeLock().lock();
        this.gih = hander;
        this.var3.writeLock().unlock();
    }


}
