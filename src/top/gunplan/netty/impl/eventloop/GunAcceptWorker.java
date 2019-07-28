package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.GunChannelException;
import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.GunNettyPipeline;
import top.gunplan.netty.common.GunNettyContext;
import top.gunplan.netty.impl.GunNettyOutputFilterChecker;
import top.gunplan.netty.protocol.GunNetOutbound;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * @author dosdrtt
 * @date
 */
public final class GunAcceptWorker extends BaseGunNettyWorker implements Runnable {
    private final SocketChannel channel;

    GunAcceptWorker(final GunNettyPipeline l, final SocketChannel channel) {
        super(l);
        this.channel = channel;
    }


    @Override
    public void work() {
        for (GunNettyFilter filter : pipeline.filters()) {
            if (!filter.doConnFilter(channel)) {
                return;
            }
        }
        try {
            final GunNetOutbound ob = this.pipeline.handel().dealConnEvent(channel.getRemoteAddress());
            pipeline.filters().forEach(f -> {
                try {
                    if (f.doOutputFilter(new GunNettyOutputFilterChecker(ob, null), channel) == GunNettyFilter.DealResult.CLOSE) {
                        channel.close();
                    }
                } catch (IOException e) {
                    GunNettyContext.logger.error(e.getMessage(), "IO ERROR");
                }
            });
        } catch (GunChannelException e) {
            this.pipeline.handel().dealExceptionEvent(e);
        } catch (IOException e) {
            this.pipeline.handel().dealExceptionEvent(new GunChannelException(e));
        }
    }
}
