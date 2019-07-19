package top.gunplan.netty.impl;

import java.nio.channels.SocketChannel;

/**
 * GunNettyChannelTransferImpl
 *
 * @author frank albert
 * @version 0.0.0.2
 * @date 2019-06-19 00:02
 */

public class GunNettyChannelTransferImpl implements GunNettyChannelTransfer<SocketChannel> {
    private long value;
    private SocketChannel channel;

    public GunNettyChannelTransferImpl() {
    }

    public GunNettyChannelTransferImpl(SocketChannel channel) {
        this.channel = channel;
    }

    public GunNettyChannelTransferImpl(long value, SocketChannel channel) {
        this.value = value;
        this.channel = channel;
    }

    @Override
    public SocketChannel getChannel() {
        return channel;
    }

    @Override
    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void setValue(long value) {
        this.value = value;
    }
}
