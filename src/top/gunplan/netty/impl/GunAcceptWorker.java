package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyFilter;
import top.gunplan.netty.GunPipeline;
import top.gunplan.netty.protocol.GunNetOutputInterface;
import top.gunplan.utils.AbstractGunBaseLogUtil;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author dosdrtt
 * @date
 */
final class GunAcceptWorker extends BaseGunNettyWorker implements Runnable {
    private final SocketChannel channel;

    GunAcceptWorker(final GunPipeline l, final SocketChannel channel) {
        super(l);
        this.channel = channel;
    }


    @Override
    public synchronized void run() {
        try {
            for (GunNettyFilter filter : pipeline.getFilters()) {
                if (!filter.doConnFilter(channel)) {
                    return;
                }
            }
            final GunNetOutputInterface ob = this.pipeline.getHandel().dealConnEvent(channel.getRemoteAddress());
            pipeline.getFilters().forEach(f -> {
                try {
                    if (f.doOutputFilter(new GunOutputFilterChecker(ob, null), channel) == GunNettyFilter.DealResult.CLOSE) {
                        channel.close();
                    }
                } catch (Exception e) {
                    AbstractGunBaseLogUtil.error(e);
                }
            });
        } catch (Exception e) {
            this.pipeline.getHandel().dealExceptionEvent(e);
        }
    }
}
