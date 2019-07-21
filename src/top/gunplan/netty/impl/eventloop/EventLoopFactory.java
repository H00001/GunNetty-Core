package top.gunplan.netty.impl.eventloop;


import java.nio.channels.SocketChannel;

public final class EventLoopFactory {
    public static GunNettyTransfer<SocketChannel> newGunNettyBaseTransfer() {
        return new GunNettyBaseTransferEventLoopImpl<>();
    }

    public static GunNettyTransfer<SocketChannel> newGunDisruptorBaseTransfer() {
        return new GunNettyDisruptorTransferEventLoopImpl<>();
    }
}
