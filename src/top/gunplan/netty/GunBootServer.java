package top.gunplan.netty;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import top.gunplan.netty.protocol.GunNetRequestInterface;
import top.gunplan.netty.protocol.GunNetResponseInterface;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

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


    void sync() throws IOException, ExecutionException, InterruptedException, URISyntaxException, NoSuchFieldException, IllegalAccessException;

    /**
     * set the Thread pool that dispose the request
     *
     * @param acceptExecuters  this Executer is used to deal with accept request
     * @param requestExecuters this Executer is used to deal with data request
     * @return this
     */

    GunBootServer setExecuters(@NotNull ExecutorService acceptExecuters, @NotNull ExecutorService requestExecuters);

    /**
     * set a deal handel implement {@link GunBootServer.GunNetHandle }
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
     * java bean
     */
    class GunNettyRequestObject {
        @Override
        public String toString() {
            return "Gun error time:" + System.currentTimeMillis();
        }

        private final SocketChannel channel;
        private final GunRequestFilterDto requestObj;

        public GunNettyRequestObject(@NotNull SocketChannel channel, @Nullable GunRequestFilterDto requestObj) {
            this.channel = channel;
            this.requestObj = requestObj;
        }

        /**
         * @return GunRequestFilterDto
         */

        public GunRequestFilterDto requestObj() {
            return requestObj;
        }

        public SocketChannel getChannel() {
            return channel;
        }
    }

    interface GunNetHandle extends GunHandle {
        /**
         * @throws GunException std exception
         * @throws IOException  error
         */
        GunNetResponseInterface dealDataEvent(GunNetRequestInterface request) throws GunException, IOException,
                NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

        /**
         * @throws GunException
         * @throws IOException  conn failt
         */
        GunNetResponseInterface dealConnEvent(GunNetRequestInterface request) throws GunException, IOException;

        /**
         *
         */
        void dealCloseEvent();

        /**
         * @param exp Exception
         */
        void dealExceptionEvent(Exception exp);
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
                this.handel.dealConnEvent(null);
            } catch (IOException e) {
                this.handel.dealExceptionEvent(e);
            }
        }
    }
}

