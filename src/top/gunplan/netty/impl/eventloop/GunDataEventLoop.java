package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunCoreEventLoop;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.Set;

/**
 * GunDataEventLoop
 *
 * @author frank albert
 * @version 0.0.0.2
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


    SelectionKey registerReadKey(SelectableChannel channel) throws IOException;
}
