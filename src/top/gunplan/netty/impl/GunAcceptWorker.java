package top.gunplan.netty.impl;
import top.gunplan.netty.GunPipeline;

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
            this.pileline.getHandel().dealConnEvent(channel);
        } catch (Exception e) {
            this.pileline.getHandel().dealExceptionEvent(e);
        }
    }
}
