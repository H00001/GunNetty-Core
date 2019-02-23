package top.gunplan.netty;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author dosdrtt
 */
public interface GunBootServer {

    /**
     * start sync waitting
     * @throws Exception
     */

    void sync() throws Exception;

    /**
     *
     * @param hander infor other {@link GunNetHander}
     */

    void setInforHander(GunInforHander hander);

    /**
     * set a deal hander implement <code>GunBootServer.GunNetHander</code>
     * @param hander Execute Class
     * @return this
     */

    GunBootServer infor(GunBootServer.GunNetHander hander);

    /**
     * @param conn A ThreadPool
     * @param deal A ThreadPool
     * @return this
     */
    GunBootServer group(@NotNull Executor conn, @NotNull Executor deal);


    /**
     *
     * @apiNote
     * @param clazz class to deal
     * @throws ClassNotFoundException
     */
    void inintObject(@NotNull Class<? extends GunH> clazz) throws Exception;

    class C3B4DTO {
        @Override
        public String toString() {
            return "Gun error time:"+System.currentTimeMillis();
        }

        private final SocketChannel var1;
        private final ByteBuffer bf;

        public C3B4DTO(@NotNull SocketChannel var1, @Nullable ByteBuffer bf) {
            this.var1 = var1;
            this.bf = bf;
        }

        public SocketChannel getVar1() {
            return var1;
        }

        public ByteBuffer getBf() {
            return bf;
        }
    }

    interface GunNetHander extends GunH {
        void dealevent(EventType t, C3B4DTO m) throws Exception;

        enum EventType {
            // private int value;


            RECEIVED, CONNRCTED, EXECPTION, CLOSEED;
        }
    }

    final class GunWorker implements Runnable {
        private final C3B4DTO s;
        private final GunBootServer.GunNetHander.EventType t;
        private final List<GunBootServer.GunNetHander> l;

        public GunWorker(List<GunBootServer.GunNetHander> l, GunBootServer.GunNetHander.EventType t, C3B4DTO s) {
            this.l = l;
            this.t = t;
            this.s = s;
        }

        @Override
         public synchronized void run() {
            for (GunBootServer.GunNetHander h : l) {
                try {
                    h.dealevent(this.t, this.s);
                } catch (Exception e) {
                    throw new GunException(e);
                }
            }
        }
    }
}
