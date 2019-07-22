package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunCoreEventLoop;

import java.nio.channels.SelectionKey;
import java.util.Set;

/**
 * GunDataEventLoop
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-22 23:29
 */
public interface GunDataEventLoop extends GunCoreEventLoop {


    /**
     * availableSelectionKey
     *
     * @return Set<SelectionKey>
     */
    Set<SelectionKey> availableSelectionKey();


    /**
     * incrAndContinueLoop
     */

    void incrAndContinueLoop();
}
