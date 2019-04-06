package top.gunplan.netty.impl;
import top.gunplan.netty.GunPilelineInterface;
import java.nio.channels.SocketChannel;

final class GunAcceptWorker extends BaseGunNettyWorker implements Runnable {
    private final SocketChannel channel;
    GunAcceptWorker(final GunPilelineInterface l, final SocketChannel channel) {
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
