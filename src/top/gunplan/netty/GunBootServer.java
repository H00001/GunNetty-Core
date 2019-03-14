package top.gunplan.netty;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import top.gunplan.nio.utils.GunBytesUtil;

import java.io.IOException;

import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author dosdrtt
 */
public interface GunBootServer {
    boolean getRunnable();

    /**
     * start sync waitting
     *
     * @throws IOException syncing's exception
     */

    void sync() throws IOException;

    /**
     * @param hander infor other {@link GunNetHander}
     */

    void setExecuters(@NotNull Executor acceptExector, @NotNull Executor requestExector);

    /**
     * set a deal hander implement <code>GunBootServer.GunNetHander</code>
     *
     * @param hander Execute Class
     * @return this
     */

    GunBootServer infor(@NotNull GunBootServer.GunNetHander hander);


    void addFilter(GunNettyFilter filter);

    /**
     * @param clazz class to deal
     * @throws ClassNotFoundException
     * @apiNote
     */
    void inintObject(@NotNull Class<? extends GunH> clazz) throws Exception;

    /**
     *
     */
    class GunNettyRequestOnject {
        @Override
        public String toString() {
            return "Gun error time:" + System.currentTimeMillis();
        }

        private final SocketChannel channel;
        private final GunFilterDto requestObj;

        public GunNettyRequestOnject(@NotNull SocketChannel channel, @Nullable GunFilterDto requestObj) {
            this.channel = channel;
            this.requestObj = requestObj;
        }

        public GunFilterDto requestObj() {
            return requestObj;
        }

        public SocketChannel getChannel() {
            return channel;
        }
    }

    interface GunNetHander extends GunH {
        void dealevent(EventType t, GunNettyRequestOnject m) throws GunException, IOException;

        enum EventType {
            /**
             *
             */
            // private int value;


            RECEIVED, CONNRCTED, EXECPTION, CLOSEED;
        }
    }


    interface GunNettyFilter {
        void doFilter(GunFilterDto filterDto);
    }

    abstract class GunNettyWorker {
        final GunBootServer.GunNetHander hander;
        final SocketChannel channel;

        GunNettyWorker(GunBootServer.GunNetHander gunNettyHanderl, SocketChannel channel) {
            this.channel = channel;
            this.hander = gunNettyHanderl;
        }
    }


    final class GunAcceptWorker extends GunNettyWorker implements Runnable {
        public GunAcceptWorker(GunBootServer.GunNetHander l, SocketChannel channel) {
            super(l, channel);
        }

        @Override
        public synchronized void run() {
            try {
                this.hander.dealevent(GunNetHander.EventType.CONNRCTED, new GunNettyRequestOnject(this.channel, null));
            } catch (IOException e) {
                throw new GunException(e);
            }
        }
    }

    final class GunRequestWorker extends GunNettyWorker implements Runnable {

        private final List<GunBootServer.GunNettyFilter> filters;

        public GunRequestWorker(List<GunBootServer.GunNettyFilter> filters, GunBootServer.GunNetHander dealHanders, SocketChannel channel) {
            super(dealHanders, channel);
            this.filters = filters;
        }

        @Override
        public synchronized void run() {
            byte[] readbata = null;
            try {
                readbata = GunBytesUtil.readFromChannel(channel, 1024);
            } catch (Exception exp) {
                try {
                    hander.dealevent(GunNetHander.EventType.EXECPTION, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (readbata == null) {
                try {
                    this.hander.dealevent(GunNetHander.EventType.CLOSEED, null);
                    channel.close();
                } catch (IOException e) {
                    throw new GunException(e);
                }


            } else {
                final GunFilterDto gunFilterObj = new GunFilterDto(readbata);
                this.filters.forEach(netty -> netty.doFilter(gunFilterObj));
                try {
                    this.hander.dealevent(GunNetHander.EventType.RECEIVED, new GunNettyRequestOnject(channel, gunFilterObj));
                } catch (Exception e) {
                    throw new GunException(e);
                }

            }
        }


    }
}

//      |
//
