package top.gunplan.netty.impl;

import top.gunplan.netty.GunNettyHandle;
import top.gunplan.netty.GunPilelineInterface;

import java.nio.channels.SocketChannel;

final class GunAcceptWorker extends BaseGunNettyWorker implements Runnable {
    GunAcceptWorker(final GunPilelineInterface l, final SocketChannel channel) {
        super(l, channel);
    }


    @Override
    public synchronized void run() {
        try {
            this.pileline.getHandel().dealConnEvent(null);
        } catch (Exception e) {
            this.pileline.getHandel().dealExceptionEvent(e);
        }
    }
}
