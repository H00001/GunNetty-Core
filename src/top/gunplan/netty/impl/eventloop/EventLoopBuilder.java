package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.impl.GunNettyCoreThreadManager;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * EventLoopBuilder
 *
 * @author frank albert
 * @version 0.0.0.1
 * @date 2019-07-23 00:10
 */
public interface EventLoopBuilder<U> {
    U build();

    EventLoopBuilder<U> andRegister(GunNettyCoreThreadManager manager);

    EventLoopBuilder<U> with(ExecutorService deal, final GunNettyPipeline pipeline) throws IOException;
}
