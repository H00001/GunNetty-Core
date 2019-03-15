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
     * set the Thread pool that dispose the request
     *
     * @param acceptExecuters  this Executer is used to deal with accept request
     * @param requestExecuters this Executer is used to deal with data request
     * @return this
     */

    GunBootServer setExecuters(@NotNull Executor acceptExecuters, @NotNull Executor requestExecuters);

    /**
     * set a deal handel implement <code>GunBootServer.GunNetHandel</code>
     *
     * @param handel Execute Class
     * @return this
     */

    GunBootServer setHandel(@NotNull GunNetHandel handel);


    /**
     * the function is used to add filter
     *
     * @param filter filter, filter the request
     */
    GunBootServer addFilter(GunNettyFilter filter);

    /**
     * @param clazz class to deal
     * @throws ClassNotFoundException
     * @apiNote
     */
    void inintObject(@NotNull Class<? extends GunHandel> clazz) throws Exception;

    /**
     *
     */
    class GunNettyRequestObject {
        @Override
        public String toString() {
            return "Gun error time:" + System.currentTimeMillis();
        }

        private final SocketChannel channel;
        private final GunFilterDto requestObj;

        public GunNettyRequestObject(@NotNull SocketChannel channel, @Nullable GunFilterDto requestObj) {
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

    interface GunNetHandel extends GunHandel {
        void dealevent(EventType t, GunNettyRequestObject m) throws GunException, IOException;

        enum EventType {
            /**
             *
             */
            // private int value;


            RECEIVED, CONNRCTED, EXECPTION, CLOSEED;
        }
    }


    abstract class BaseGunNettyWorker {
        final GunNetHandel handel;
        final SocketChannel channel;

        BaseGunNettyWorker(final GunNetHandel gunNettyHanderl, final SocketChannel channel) {
            this.channel = channel;
            this.handel = gunNettyHanderl;
        }
    }


    final class GunAcceptWorker extends BaseGunNettyWorker implements Runnable {
        public GunAcceptWorker(final GunNetHandel l, final SocketChannel channel) {
            super(l, channel);
        }

        @Override
        public synchronized void run() {
            try {
                this.handel.dealevent(GunNetHandel.EventType.CONNRCTED, new GunNettyRequestObject(this.channel, null));
            } catch (IOException e) {
                throw new GunException(e);
            }
        }
    }

    final class GunRequestWorker extends BaseGunNettyWorker implements Runnable {

        private final List<GunNettyFilter> filters;

        public GunRequestWorker(final List<GunNettyFilter> filters, final GunNetHandel dealHanders, final SocketChannel channel) {
            super(dealHanders, channel);
            this.filters = filters;
        }

        @Override
        public synchronized void run() {
            byte[] readbata = null;
            try {
                readbata = GunBytesUtil.readFromChannel(channel, 1024);
            }
            catch (Exception exp) {
                try {
                    handel.dealevent(GunNetHandel.EventType.EXECPTION, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (readbata == null) {
                try {
                    this.handel.dealevent(GunNetHandel.EventType.CLOSEED, null);
                    channel.close();
                } catch (IOException e) {
                    throw new GunException(e);
                }
            }
            else {
                final GunFilterDto gunFilterObj = new GunFilterDto(readbata);
                this.filters.forEach(netty -> netty.doRequestFilter(gunFilterObj));
                try {
                    this.handel.dealevent(GunNetHandel.EventType.RECEIVED, new GunNettyRequestObject(channel, gunFilterObj));
                } catch (Exception e) {
                    throw new GunException(e);
                }
                this.filters.forEach(netty -> netty.doResponseFilter(gunFilterObj));

            }
        }


    }
}

