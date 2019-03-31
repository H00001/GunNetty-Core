package top.gunplan.netty.impl;

import top.gunplan.netty.GunNetHandle;

import java.io.IOException;
import java.nio.channels.SocketChannel;

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
