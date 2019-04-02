package top.gunplan.netty;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import top.gunplan.netty.impl.GunRequestFilterDto;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;


/**
 * server interface ,has kinds of implements but now we have only one
 * {@link top.gunplan.netty.impl.GunBootServerImpl}
 *
 * @author dosdrtt
 * @since 0.0.0.1
 */
public interface GunBootServer {
    /**
     * get the status of server
     *
     * @return the server 's status
     */
    boolean isRunnable();

    /**
     * start sync server and wait
     *
     * @throws IOException syncing's exception
     */


    void sync() throws Exception;

    /**
     * set the Thread pool that dispose the request
     *
     * @param acceptExecuters  this Executer is used to deal with accept request
     * @param requestExecuters this Executer is used to deal with data request
     * @return this
     */

    GunBootServer setExecuters(@NotNull ExecutorService acceptExecuters, @NotNull ExecutorService requestExecuters);

    /**
     * set a deal handel implement {@link GunNetHandle }
     *
     * @param handel Execute Class
     * @return GunBootServer:this chain style
     */

    GunBootServer setHandel(@NotNull GunNetHandle handel);


    /**
     * the function is used to add filter
     *
     * @param filter filter, filter the request
     * @return GunBootServer chain style
     */
    GunBootServer addFilter(GunNettyFilter filter);


    /**
     * @param clazz class to deal
     * @throws ClassNotFoundException class not found
     * @apiNote
     */
    void inintObject(@NotNull Class<? extends GunHandle> clazz) throws Exception;


}

