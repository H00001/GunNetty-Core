package top.gunplan.netty;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import top.gunplan.nio.utils.GunBytesUtil;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author dosdrtt
 */
public interface GunBootServer {
    /**
     * @return the server 's status
     */
    boolean isRunnable();

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
     * set a deal handel implement <code>GunBootServer.GunNetHandle</code>
     *
     * @param handel Execute Class
     * @return this chain style
     */

    GunBootServer setHandel(@NotNull GunNetHandle handel);


    /**
     * the function is used to add filter
     *
     * @param filter filter, filter the request
     */
    GunBootServer addFilter(GunNettyFilter filter);

    /**
     * @param clazz class to deal
     * @throws ClassNotFoundException class not found
     * @apiNote
     */
    void inintObject(@NotNull Class<? extends GunHandle> clazz) throws Exception;

    /**
     * GunNettyRequestObject is used to resopnse
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

    interface GunNetHandle extends GunHandle {
        /**
         * @param t EventType
         * @param m GunNettyRequestObject
         * @throws GunException std exception
         * @throws IOException  error
         */
        void dealDataEvent(EventType t, GunNettyRequestObject m) throws GunException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

        /**
         * @param t EventType
         * @param m GunNettyRequestObject
         * @throws GunException
         * @throws IOException  conn failt
         */
        void dealConnEvent(EventType t, GunNettyRequestObject m) throws GunException, IOException;

        /**
         *
         */
        void dealCloseEvent();

        /**
         * @param exp Exception
         */
        void dealExceptionEvent(Exception exp);

        enum EventType {
            /**
             *
             */


            RECEIVED, CONNRCTED, EXECPTION, CLOSEED;
        }
    }


    abstract class BaseGunNettyWorker {
        final GunNetHandle handel;
        final SocketChannel channel;

        BaseGunNettyWorker(final GunNetHandle gunNettyHanderl, final SocketChannel channel) {
            this.channel = channel;
            this.handel = gunNettyHanderl;
        }
    }


    final class GunAcceptWorker extends BaseGunNettyWorker implements Runnable {
        public GunAcceptWorker(final GunNetHandle l, final SocketChannel channel) {
            super(l, channel);
        }

        @Override
        public synchronized void run() {
            try {
                this.handel.dealConnEvent(GunNetHandle.EventType.CONNRCTED, new GunNettyRequestObject(this.channel, null));
            } catch (IOException e) {
                this.handel.dealExceptionEvent(e);
            }
        }
    }

    final class GunRequestWorker extends BaseGunNettyWorker implements Runnable {

        private final List<GunNettyFilter> filters;

        public GunRequestWorker(final List<GunNettyFilter> filters, final GunNetHandle dealHanders, final SocketChannel channel) {
            super(dealHanders, channel);
            this.filters = filters;
        }

        @Override
        public synchronized void run() {
            byte[] readbata = null;
            try {
                readbata = GunBytesUtil.readFromChannel(channel, 1024);
                if (readbata == null) {
                    this.handel.dealCloseEvent();
                    channel.close();
                }
            } catch (Exception e) {
                this.handel.dealExceptionEvent(e);
            }
            if (readbata != null) {
                final GunFilterDto gunFilterObj = new GunFilterDto(readbata);
                this.filters.forEach(netty -> netty.doRequestFilter(gunFilterObj));
                try {
                    this.handel.dealDataEvent(GunNetHandle.EventType.RECEIVED, new GunNettyRequestObject(channel, gunFilterObj));
                    this.filters.forEach(netty -> netty.doResponseFilter(gunFilterObj));
                    if (gunFilterObj.getSrc() != null) {
                        super.channel.write(ByteBuffer.wrap(gunFilterObj.getSrc()));
                    }
                } catch (IOException e) {
                    this.handel.dealExceptionEvent(e);
                }
            }
        }
    }
}

