package top.gunplan.netty.impl.eventloop;

import java.nio.channels.SelectionKey;

/**
 * AbstractGunCoreAioEventLoop
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-06-09 10:25
 */

public abstract class AbstractGunCoreAioEventLoop extends AbstractGunCoreEventLoop {
    public AbstractGunCoreAioEventLoop() {
        super();
    }

    /**
     * aio running method
     */

    @Override
    public void dealEvent(SelectionKey key) throws Exception {

    }
}
