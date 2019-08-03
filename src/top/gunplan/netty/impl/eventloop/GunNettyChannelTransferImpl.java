package top.gunplan.netty.impl.eventloop;

import top.gunplan.netty.impl.GunNettyChannelTransfer;

import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelTransferImpl
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-06-19 00:02
 */

public class GunNettyChannelTransferImpl implements GunNettyChannelTransfer<SocketChannel> {
    private volatile long key;
    private volatile SocketChannel channel;

    GunNettyChannelTransferImpl() {
    }


    GunNettyChannelTransferImpl(long value, SocketChannel key) {
        this.key = value;
        this.channel = key;
    }


    @Override
    public SocketChannel channel() {
        return channel;
    }

    @Override
    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void setKey(long key) {
        this.key = key;
    }

    @Override
    public long key() {
        return key;
    }
}
